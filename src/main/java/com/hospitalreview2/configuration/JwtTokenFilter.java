package com.hospitalreview2.configuration;

import com.hospitalreview2.domain.User;
import com.hospitalreview2.service.UserService;
import com.hospitalreview2.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter { // OncePerRequestFilter는 입장할때마다 매번 티켓을 보여주는 방식

    private final UserService userService;
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 권한 주거나 안주기 , 입구라고 생각하면 됨
        // 개찰구 역할, 카드를 찍으면 열어주는 기능을 만들거임
        // 현재는 모두 닫혀있습니다.

        //언제 막아야 할까요?
        //1. Token을 안가지고 온다. —> Request할 때 Token을 안넣고 호출하는 경우
        //2. 적절하지 않은 Token을 가지고 온다      -> 이거는 어떤 부분에서 확인하는지 알수있을까요??
        //3. 지난 Token을 가지고 온다

        // 약간 토큰 값 String으로 뽑아내는 느낌 ;변수 선언
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authorizationHeader:{}", authorizationHeader);

        // token이 없으면 에러처리하기
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // token 분리
        String token;
        try {
            token = authorizationHeader.split(" ")[1];
        } catch (Exception e) { // 잘못된 토큰을 보냈는지
            log.error("token 추출에 실패 했습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        // Token이 만료되었는지 체크
        if (JwtTokenUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Token에서 Claim에서 UserName 꺼내기
        String userName = JwtTokenUtil.getUserName(token, secretKey);
        log.info("userName:{}", userName);

        // userDetail 가져오기
        User user = userService.getUserByUserName(userName);
        log.info("userRole:{}", user.getRole());


        // 문열어주기, Role 바인딩
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), null, List.of(new SimpleGrantedAuthority(user.getRole().name())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
