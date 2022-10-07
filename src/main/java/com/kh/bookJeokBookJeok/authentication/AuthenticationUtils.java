package com.kh.bookJeokBookJeok.authentication;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class AuthenticationUtils {
    @Value("${ADMIN_EMAIL}")
    private String ADMIN_EMAIL;
    @Getter
    private List<String> rolesForAdmin = List.of("ROLE_ADMIN", "ROLE_USER");
    @Getter
    private List<String> rolesForUser = List.of("ROLE_USER");
    public List<String>  createRoleByEmail(String email) {
        if(email.equals(ADMIN_EMAIL)) {
            return rolesForAdmin;
        } else {
            return rolesForUser;
        }
    }
}
