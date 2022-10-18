package com.kh.bookJeokBookJeok.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.bookJeokBookJeok.authentication.AuthDto;
import com.kh.bookJeokBookJeok.authentication.JwtTokenizer;
import com.kh.bookJeokBookJeok.member.entity.Member;
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
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtTokenizer jwtTokenizer;
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //요청으로부터 아이디와 패스워드 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        AuthDto authDto = objectMapper.readValue(request.getInputStream(), AuthDto.class);

        //토큰을 생성`
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authDto.getUsername(), authDto.getPassword());
        //AuthenticationManager에게 인증 위임
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Member member = (Member) authResult.getPrincipal();

        String accessToken = createAccessToken(member);
        String refreshToken = createRefreshToken(member);

        //response header에 싣기
        response.setHeader("Authorization", "Bearer" + accessToken);
        response.setHeader("refreshToekn", refreshToken);
    }

    private String createAccessToken(Member member) {
        //claims 파싱
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", member.getRoles());

        String subject = member.getEmail();
        return jwtTokenizer.createAccessToken(subject, claims);
    }

    private String createRefreshToken(Member member) {
        String subject = member.getEmail();
        return jwtTokenizer.createRefreshToken(subject);
    }
}
