package com.hospitalreview2.conroller;

import com.hospitalreview2.domain.Response;
import com.hospitalreview2.domain.dto.*;
import com.hospitalreview2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 수동테스트 성공
    // 아래 과정에 문제가 없었다는 것.
    // Client → Controller → Service → Dto → Response<T> → UserJoinResponse → Client
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto userDto = userService.join(userJoinRequest);
        return Response.success(new UserJoinResponse(userDto.getUserName(), userDto.getEmailAddress()));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        String token = userService.login(userLoginRequest.getUserName(), userLoginRequest.getPassword());
        return Response.success(new UserLoginResponse(token));
    }
}
