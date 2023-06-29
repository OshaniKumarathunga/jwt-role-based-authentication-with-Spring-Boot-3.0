package com.springboot3.jwtauthentication.controller;

import com.springboot3.jwtauthentication.Config.CustomUserDetailsService;
import com.springboot3.jwtauthentication.dto.AuthRequestDto;
import com.springboot3.jwtauthentication.dto.AuthResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/login")
    public AuthResponseDto createToken(@RequestBody AuthRequestDto authRequestDto) throws Exception {
        return customUserDetailsService.createJwtToken(authRequestDto);
    }

}
