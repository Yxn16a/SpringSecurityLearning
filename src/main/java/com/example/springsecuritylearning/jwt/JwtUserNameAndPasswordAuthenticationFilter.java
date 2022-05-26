package com.example.springsecuritylearning.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

// this class is to verify the credentials
public class JwtUserNameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtUserNameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager1;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
       try {
           UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                   .readValue(request.getInputStream(),
                           UsernameAndPasswordAuthenticationRequest.class);

           Authentication authentication = new UsernamePasswordAuthenticationToken(
               authenticationRequest.getUsername(),
               authenticationRequest.getPassword()
           );
           // this check if the user name exit and then check the password
         Authentication authenticate =  authenticationManager.authenticate(authentication);
           return authenticate; // then if everything passes then we will aunthenticate the user
       }catch (IOException e){
           return super.attemptAuthentication(request,response);
       }
    }
    // this will be invoken afte successful authentication
    // request filters are the classes that filters the request before it reaches the API
    // example of those class includes  UsernamePasswordAuthenticationFilter
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
 String key = "securesecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecuresecure";
      String token= Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities",authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor(key.getBytes()))
                .compact();
        response.addHeader("Authorization", "Bearer" + token);

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
