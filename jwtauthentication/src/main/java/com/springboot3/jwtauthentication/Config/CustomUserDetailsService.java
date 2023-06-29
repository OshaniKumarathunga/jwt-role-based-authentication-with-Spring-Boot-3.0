package com.springboot3.jwtauthentication.Config;

import com.springboot3.jwtauthentication.dto.AuthRequestDto;
import com.springboot3.jwtauthentication.dto.AuthResponseDto;
import com.springboot3.jwtauthentication.entity.User;
import com.springboot3.jwtauthentication.repo.UserRepo;
import com.springboot3.jwtauthentication.util.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDto createJwtToken(AuthRequestDto authRequestDto) throws Exception {
        String userName = authRequestDto.getUsername();
        String Password = authRequestDto.getPassword();
        authenticate(userName, Password);

        UserDetails userDetails = loadUserByUsername(userName);
        String newGeneratedToken = jwtGenerator.generateToken(userDetails);

        User user = userRepo.findById(userName).get();
        return new AuthResponseDto(user , newGeneratedToken);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username).get();
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    getAuthority(user)
            );
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE" + role.getRoleName()));
        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
