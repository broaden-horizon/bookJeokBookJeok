package com.kh.bookJeokBookJeok.authentication;

import com.kh.bookJeokBookJeok.member.entity.Member;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenizer {
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration.access-token}")
    private int accessTokenExpiration;
    @Value("${jwt.expiration.refresh-token}")
    private int refreshTokenExpiration;

    public Map<String, Object> getClaimsAsMap(String jws) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws).getBody();
    }

    public String createAccessToken(String subject,
                                    Map<String, Object> claims) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Date expiration = getExpiration(accessTokenExpiration);
        return Jwts.builder()
                .setSubject(subject)
                .setClaims(claims)
                .setExpiration(expiration)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key)
                .compact();
    }
    public String createRefreshToken(String subject) {
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Date expiration = getExpiration(refreshTokenExpiration);
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key)
                .compact();
    }
    private Date getExpiration(int tokenExpiration) {
        Calendar date = Calendar.getInstance();
        date.add(Calendar.MINUTE, tokenExpiration);
        return date.getTime();
    }
}
