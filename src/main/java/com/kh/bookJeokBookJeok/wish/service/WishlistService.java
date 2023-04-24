package com.kh.bookJeokBookJeok.wish.service;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishlistRepository;
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

    public Wish getWishlist(long wishlistId) {
        //토큰으로부터 멤버 추출
        Member memberFound = memberService.verifyMemberFromToken();
        //해당 멤버가 작성한 위시리스트가 맞는지 확인
        Wish verifiedWish = verifyWishlistWrittenByMember(wishlistId, memberFound.getMemberId());

        return verifiedWish;
    }
    public Wish update(Wish wish) {
        //토큰으로부터 멤버 추출
        Member memberFound = memberService.verifyMemberFromToken();
        //해당 멤버가 작성한 위시리스트가 맞는지 확인
        Wish verifiedWish = verifyWishlistWrittenByMember(wish.getWishlistId(), memberFound.getMemberId());
        Optional.of(wish.getDueDate()).ifPresent(
                dueDate -> verifiedWish.setDueDate(dueDate)
        );
        Optional.of(wish.isNotice()).ifPresent(
                isNotice -> verifiedWish.setNotice(isNotice)
        );
        return wishlistRepository.save(verifiedWish);
    }

    private Wish verifyWishlist(long wishlistId) {
        Optional<Wish> optionalWishlist = wishlistRepository.findById(wishlistId);
        return optionalWishlist.orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISHLIST_NOT_FOUND));
    }
    private Wish verifyWishlistWrittenByMember(long wishlistId, long memberId) {
        Wish verifiedWish = verifyWishlist(wishlistId);
        if(memberId != verifiedWish.getMember().getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.WISHLIST_NOT_WRITTEN_BY_MEMBER);
        }
        return verifiedWish;
    }

    public Wish create(Wish wish) {
        Member memberFound = memberService.verifyMemberFromToken();
        //같은 책을 기존에 등록했는지 체크
        checkWishListExist(memberFound, wish.getIsbn());

        wish.setMember(memberFound);
        return wishlistRepository.save(wish);
    }

    void checkWishListExist(Member member, String isbn) {
        Optional<Wish> wishLists = wishlistRepository.findByMemberAndIsbn(member, isbn);
        if(wishLists.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.WISHLIST_EXISTS);
        }
    }


}
