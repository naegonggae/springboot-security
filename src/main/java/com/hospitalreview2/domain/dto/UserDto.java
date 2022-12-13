package com.hospitalreview2.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private String emailAddress;
}
