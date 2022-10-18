package com.kh.bookJeokBookJeok.authentication;

import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@AllArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private MemberRepository memberRepository;
    private AuthenticationUtils authenticationUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberFound = memberRepository.findByEmail(username).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        MemberDetails memberDetails = new MemberDetails(memberFound);
        return memberDetails;
    }

    public final class MemberDetails extends Member implements UserDetails {
        MemberDetails(Member member) {
            super(
                member.getMemberId(),
                member.getEmail(),
                member.getPassword(),
                member.getRoles()
            );
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authenticationUtils.convertStringRolesToGrantedAuthorities(getRoles());
        }

        @Override
        public String getPassword() {
            return super.getPassword();
        }

        @Override
        public String getUsername() {
            return super.getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
