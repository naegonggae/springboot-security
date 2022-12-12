package com.hospitalreview2.service;

import com.hospitalreview2.domain.dto.UserDto;
import com.hospitalreview2.domain.dto.UserJoinRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDto join(UserJoinRequest request) {
        return new UserDto("", "","");
    }
}
