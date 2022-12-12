package com.hospitalreview2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private String userName;
    private String password;
    private String emailAddress;
}
