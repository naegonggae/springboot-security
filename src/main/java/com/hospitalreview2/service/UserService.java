package com.hospitalreview2.service;

import com.hospitalreview2.domain.User;
import com.hospitalreview2.domain.dto.UserDto;
import com.hospitalreview2.domain.dto.UserJoinRequest;
import com.hospitalreview2.exception.ErrorCode;
import com.hospitalreview2.exception.HospitalReviewAppException;
import com.hospitalreview2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserDto join(UserJoinRequest request) {
        // 비즈니스 로직 - 회원 가입
        // 회원 userName(id) 중복 Check
        // 중복이면 회원가입 x --> Exception(예외)발생
        // 있으면 에러처리
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME, String.format("Username:%s", request.getUserName() ));
                });

        // 회원가입 .save() 여기서 UserJoinRequest이게 아니라 entity를 받아야함으로 UserJoinRequest에 toEntity 만들어야함
        User savedUser = userRepository.save(request.toEntity(encoder.encode(request.getPassword())));
        return UserDto.builder() // User를 UserDto로 만들어줌
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .emailAddress(savedUser.getEmailAddress())
                .build();
    }

    public String login(String userName, String password) {

        // username이 있는지 여부 확인
        // 없으면 Not found 발생
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new HospitalReviewAppException(ErrorCode.NOT_FOUND, String.format("%s는 가입된 적이 없습니다.", userName)));

        // password 일치하는지 여부 확인
        if (!encoder.matches(password, user.getPassword())) {
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, String.format("userName 또는 password가 잘못됐습니다."));
        }

        // 두가지 확인중 예외 안났으면 Token 발행

        return "";
    }
}
