package com.springboot3.jwtauthentication.dto;

import com.springboot3.jwtauthentication.entity.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthResponseDto {
    private User user;
    private String jwtToken;

    public AuthResponseDto(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }
}
