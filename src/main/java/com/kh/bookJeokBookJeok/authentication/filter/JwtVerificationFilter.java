package com.kh.bookJeokBookJeok.authentication.filter;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import com.kh.bookJeokBookJeok.authentication.JwtTokenizer;
import lombok.AllArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    JwtTokenizer jwtTokenizer;
    AuthenticationUtils authenticationUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            //클레임 파싱하여
            Map<String, Object> claims = verifiyJws(request);
            //securityContext에 권한 정보 저장 (짧게)
            setAuthenticationToContext(claims);
        } catch (Exception e) {
            request.setAttribute("exception", e);
        }
        filterChain.doFilter(request, response);
    }

    //필터 작동하지 않는 경우
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String authorization = request.getHeader("Authorization");
        return authorization == null || !authorization.startsWith("Bearer");
    }

    private void setAuthenticationToContext(Map<String, Object> claims) {
        //이메일만 파싱하여 프린시플에 저장하지 않고, 클레임 자체를 저장.
//        String username = (String) claims.get("username");
        List<GrantedAuthority> roles = authenticationUtils.convertStringRolesToGrantedAuthorities((List) claims.get("roles"));
        System.out.println("memberId: "+ claims.get("memberId"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(claims, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Map<String, Object> verifiyJws(HttpServletRequest request) {
        String jws = request.getHeader("Authorization").replace("Bearer", "");
        Map<String, Object> claims = jwtTokenizer.getClaimsAsMap(jws);
        return claims;
    }
}
