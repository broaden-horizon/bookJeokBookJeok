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

    public Wishlist getWishlist(long wishlistId) {
        //토큰으로부터 멤버 추출
        Member memberFound = memberService.verifyMemberFromToken();
        //해당 멤버가 작성한 위시리스트가 맞는지 확인
        Wishlist verifiedWishlist = verifyWishlistWrittenByMember(wishlistId, memberFound.getMemberId());

        return verifiedWishlist;
    }
    public Wishlist update(Wishlist wishlist) {
        //토큰으로부터 멤버 추출
        Member memberFound = memberService.verifyMemberFromToken();
        //해당 멤버가 작성한 위시리스트가 맞는지 확인
        Wishlist verifiedWishlist = verifyWishlistWrittenByMember(wishlist.getWishlistId(), memberFound.getMemberId());
        Optional.of(wishlist.getDueDate()).ifPresent(
                dueDate -> verifiedWishlist.setDueDate(dueDate)
        );
        Optional.of(wishlist.isNotice()).ifPresent(
                isNotice -> verifiedWishlist.setNotice(isNotice)
        );
        return wishlistRepository.save(verifiedWishlist);
    }

    private Wishlist verifyWishlist(long wishlistId) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);
        return optionalWishlist.orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISHLIST_NOT_FOUND));
    }
    private Wishlist verifyWishlistWrittenByMember(long wishlistId, long memberId) {
        Wishlist verifiedWishlist = verifyWishlist(wishlistId);
        if(memberId != verifiedWishlist.getMember().getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.WISHLIST_NOT_WRITTEN_BY_MEMBER);
        }
        return verifiedWishlist;
    }

    public Wishlist create(Wishlist wishlist) {
        Member memberFound = memberService.verifyMemberFromToken();
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
