package com.hospitalreview2.domain.dto;

import com.hospitalreview2.domain.User;
import com.hospitalreview2.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJoinRequest {
    private String userName;
    private String password;
    private String emailAddress;

    public User toEntity(String password) { // UserJoinRequest를 User로 만들어줌
        return User.builder()
                .userName(this.userName)
                .password(password)
                .emailAddress(this.emailAddress)
                .role(UserRole.USER)
                .build();
    }
}
