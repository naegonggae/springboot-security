package com.hospitalreview2.domain.dto;

import com.hospitalreview2.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserJoinRequest {
    private String userName;
    private String password;
    private String emailAddress;

    public User toEntity() { // UserJoinRequest를 User로 만들어줌
        return User.builder()
                .userName(this.userName)
                .password(this.password)
                .emailAddress(this.emailAddress)
                .build();
    }
}
