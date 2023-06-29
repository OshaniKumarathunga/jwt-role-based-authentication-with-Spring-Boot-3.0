package com.springboot3.jwtauthentication.controller;

import com.springboot3.jwtauthentication.Config.CustomUserDetailsService;
import com.springboot3.jwtauthentication.dto.AuthRequestDto;
import com.springboot3.jwtauthentication.dto.AuthResponseDto;
import com.springboot3.jwtauthentication.dto.RegisterDto;
import com.springboot3.jwtauthentication.entity.Role;
import com.springboot3.jwtauthentication.entity.User;
import com.springboot3.jwtauthentication.repo.RoleRepo;
import com.springboot3.jwtauthentication.repo.UserRepo;
import com.springboot3.jwtauthentication.util.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtGenerator jwtGenerator;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto)  {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getUsername(),
                        authRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token) , HttpStatus.OK);


    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if (userRepo.existsByUserName(registerDto.getUserName())){
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUserName(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role role = roleRepo.findById("USER").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);

        userRepo.save(user);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

}
