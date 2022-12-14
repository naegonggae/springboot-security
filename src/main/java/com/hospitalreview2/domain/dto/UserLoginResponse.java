package com.hospitalreview2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@Getter
public class UserLoginResponse {
    private String token;
}
