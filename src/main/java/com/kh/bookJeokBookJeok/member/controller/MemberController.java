package com.kh.bookJeokBookJeok.member.controller;

import com.kh.bookJeokBookJeok.dto.MultiResponseDto;
import com.kh.bookJeokBookJeok.dto.SingleResponseDto;
import com.kh.bookJeokBookJeok.member.dto.MemberDto;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.mapper.MemberMapper;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequestMapping("/members")
@AllArgsConstructor
@Validated
@Slf4j
public class MemberController {
    MemberService memberService;
    MemberMapper memberMapper;

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberPostDto) {
        Member memberAdd = memberMapper.memberPostDtoToMember(memberPostDto);
        Member member = memberService.createMember(memberAdd);

        return new ResponseEntity(new SingleResponseDto<>(
                memberMapper.memberToMemberResponseDto(member)), HttpStatus.CREATED);
    }

    @PatchMapping("/{memberId}")
    public ResponseEntity patchMember(@PathVariable @Min(1) Long memberId,
                                      @Valid @RequestBody MemberDto.Patch memberPatchDto) {
        Member memberUpdate = memberMapper.memberPatchDtoToMember(memberPatchDto);
        memberUpdate.setMemberId(memberId);
        Member member = memberService.updateMember(memberUpdate);
        return new ResponseEntity(new SingleResponseDto<>(
                memberMapper.memberToMemberResponseDto(member)), HttpStatus.OK);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity getMember(@PathVariable @Min(1) Long memberId) {
        Member memberFound = memberService.findVerifiedMember(memberId);

        return new ResponseEntity(new SingleResponseDto<>(
                memberMapper.memberToMemberResponseDto(memberFound)), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity getMembers(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size ) {
        Page<Member> membersPage = memberService.findMembers(page - 1, size);
        List<MemberDto.Response> memberDtos = memberMapper.listOfMembersToListOfMemberResponseDtos(membersPage.getContent());
        return new ResponseEntity(
                new MultiResponseDto<>(memberDtos, membersPage), HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}")
    public void deleteMember(@PathVariable @Min(1) Long memberId) {
        memberService.deleteMember(memberId);
    }
}

