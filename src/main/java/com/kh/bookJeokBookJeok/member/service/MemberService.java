package com.kh.bookJeokBookJeok.member.service;

import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        checkEmailExist(member.getEmail());
        checkNicknameExist(member.getNickname());
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
