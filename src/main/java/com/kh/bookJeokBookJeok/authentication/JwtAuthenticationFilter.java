package com.kh.bookJeokBookJeok.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
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

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    AuthenticationManager authenticationManager;
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //요청으로부터 아이디와 패스워드 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        AuthDto authDto = objectMapper.readValue(request.getInputStream(), AuthDto.class);

        //토큰을 생성
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
        //AuthenticationManager에게 인증 위임
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
