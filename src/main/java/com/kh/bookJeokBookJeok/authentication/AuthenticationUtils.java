package com.kh.bookJeokBookJeok.authentication;

import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AuthenticationUtils {
    @Value("${ADMIN_EMAIL}")
    private String ADMIN_EMAIL;
    @Getter
    private List<String> rolesForAdmin = List.of("ROLE_ADMIN", "ROLE_USER");
    @Getter
    private List<String> rolesForUser = List.of("ROLE_USER");
    public List<String> createRolesByEmail(String email) {
        if(email.equals(ADMIN_EMAIL)) {
            return rolesForAdmin;
        } else {
            return rolesForUser;
        }
    }

    public List<GrantedAuthority> convertStringRolesToGrantedAuthorities(List<String> stringRoles) {
        return stringRoles.stream().map(str -> new SimpleGrantedAuthority(str)).collect(Collectors.toList());
    }

    public Long extractMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            throw new BusinessLogicException(ExceptionCode.AUTHENTICATION_NOT_FOUND);
        }
        Map<String, Object> claims = (Map) authentication.getPrincipal();
        return Long.valueOf((int) claims.get("memberId"));
    }
}
