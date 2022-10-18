package com.kh.bookJeokBookJeok.member.mapper;

import com.kh.bookJeokBookJeok.member.dto.MemberDto;
import com.kh.bookJeokBookJeok.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post memberPostDto);
    Member memberPatchDtoToMember(MemberDto.Patch memberPatchDto);
    MemberDto.Response memberToMemberResponseDto(Member member);
    List<MemberDto.Response> listOfMembersToListOfMemberResponseDtos(List<Member> members);
}
