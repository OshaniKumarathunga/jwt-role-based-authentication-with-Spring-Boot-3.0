package com.springboot3.jwtauthentication.Config;

import com.springboot3.jwtauthentication.util.JwtGenerator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getJWTFromRequest(request);

        if (StringUtils.hasText(token) && jwtGenerator.validateJwtToken(token)) {
            String username = jwtGenerator.getUserNameFromToken(token);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
//      if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
//          jwtToken = requestTokenHeader.substring(7);
//
//          try {
//              userName = jwtGenerator.getUserNameFromToken(jwtToken);
//          }catch (IllegalArgumentException e) {
//              System.out.println("Unable to get JWT Token");
//          } catch (ExpiredJwtException e) {
//              System.out.println("JWT Token has expired");
//          }
//      }else {
//          System.out.println("Jwt token does not start with Bearer");
//      }

//      if (userName!=null && SecurityContextHolder.getContext().getAuthentication()== null){
//          UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
//          if (jwtGenerator.validateJwtToken(jwtToken , userDetails)){
//              UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =  new UsernamePasswordAuthenticationToken(
//                      userDetails,
//                      null ,
//                      userDetails.getAuthorities());
//
//              usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//              SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//          }
//      }
        filterChain.doFilter(request, response);
    }
    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }



}
