package com.kh.bookJeokBookJeok.wishlist.service;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.wishlist.entity.Wishlist;
import com.kh.bookJeokBookJeok.wishlist.repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class WishlistService {
    WishlistRepository wishlistRepository;
    AuthenticationUtils authenticationUtils;
    MemberService memberService;

    public Wishlist create(Wishlist wishlist) {
        long memberId = authenticationUtils.extractMemberId();
        Member memberFound = memberService.findVerifiedMember(memberId);
        //같은 책을 기존에 등록했는지 체크
        checkWishListExist(memberFound, wishlist.getIsbn());

        wishlist.setMember(memberFound);
        return wishlistRepository.save(wishlist);
    }

    void checkWishListExist(Member member, String isbn) {
        Optional<Wishlist> wishLists = wishlistRepository.findByMemberAndIsbn(member, isbn);
        if(wishLists.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.WISHLIST_EXISTS);
        }
    }


}
