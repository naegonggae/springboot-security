package com.hospitalreview2.service;

import com.hospitalreview2.domain.User;
import com.hospitalreview2.domain.dto.UserDto;
import com.hospitalreview2.domain.dto.UserJoinRequest;
import com.hospitalreview2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto join(UserJoinRequest request) {
        // 비즈니스 로직 - 회원 가입
        // 회원 userName(id) 중복 Check
        // 중복이면 회원가입 x --> Exception(예외)발생
        // 있으면 에러처리
        userRepository.findByUserName(request.getUserName())
                .ifPresent(user -> new RuntimeException("해당 userName이 중복됩니다."));

        // 회원가입 .save() 여기서 UserJoinRequest이게 아니라 entity를 받아야함으로 UserJoinRequest에 toEntity 만들어야함
        User savedUser = userRepository.save(request.toEntity());
        return UserDto.builder() // User를 UserDto로 만들어줌
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .emailAddress(savedUser.getEmailAddress())
                .build();
    }
}
