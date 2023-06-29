package com.springboot3.jwtauthentication.dto;

import com.springboot3.jwtauthentication.entity.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthResponseDto {
    private String jwtToken;
    private String tokenType = "Bearer ";

    public AuthResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
