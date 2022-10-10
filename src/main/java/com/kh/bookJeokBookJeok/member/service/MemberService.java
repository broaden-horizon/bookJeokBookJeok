package com.kh.bookJeokBookJeok.member.service;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MemberService {
    private MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationUtils authenticationUtils;

    public Member createMember(Member member) {
        //기본적인 확인
        checkEmailExist(member.getEmail());
        checkNicknameExist(member.getNickname());

        //비밀번호 암호화하여 저장
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        //이메일을 통해 role 할당
        member.setRoles(authenticationUtils.createRolesByEmail(member.getEmail()));

        return memberRepository.save(member);
    }

    public Page<Member> findMembers(int page, int size) {
        return memberRepository.findAll(PageRequest.of(page, size, Sort.by("memberId").descending()));
    }
    public Member updateMember(Member member) {
        Member memberFound = findVerifiedMember(member.getMemberId());
        Optional.ofNullable(member.getNickname())
                .ifPresent((nickName) -> {
                    checkNicknameExist(nickName);
                    memberFound.setNickname(nickName);
                });
        Optional.ofNullable(member.getPassword())
                .ifPresent((pw) -> memberFound.setPassword(pw));
        return memberRepository.save(memberFound);
    }


    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member memberFound = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return memberFound;
    }

    public List<Member> findAll() {
        return (List<Member>) memberRepository.findAll();
    }

    public void deleteMember(long memberId) {
        Member memberFound = findVerifiedMember(memberId);
        memberFound.setMemberStatus(Member.MemberStatus.MEMBER_WITHDRAW);
        memberRepository.save(memberFound);
    }
    private void checkEmailExist(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_WITH_EMAIL_EXISTS);
        }
    }
    private void checkNicknameExist(String nickname) {
        Optional<Member> member = memberRepository.findByNickname(nickname);
        if(member.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_WITH_NICKNAME_EXISTS);
        }
    }



}
