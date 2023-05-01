package com.kh.bookJeokBookJeok.wish.service;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class WishService {
    WishRepository wishRepository;
    AuthenticationUtils authenticationUtils;
    MemberService memberService;

    public Wish getWish(long wishId) {
        //토큰으로부터 멤버 추출
        Member memberFound = memberService.verifyMemberFromToken();
        //해당 멤버가 작성한 위시리스트가 맞는지 확인
        Wish verifiedWish = verifyWishlistWrittenByMember(wishId, memberFound.getMemberId());

        return verifiedWish;
    }
    public Wish update(Wish wish, Long wishId) {
        //토큰으로부터 멤버 추출
        Member memberFound = memberService.verifyMemberFromToken();
        //해당 멤버가 작성한 위시리스트가 맞는지 확인
        Wish verifiedWish = verifyWishlistWrittenByMember(wishId, memberFound.getMemberId());
        Optional.of(wish.getDueDate()).ifPresent(
                dueDate -> verifiedWish.setDueDate(dueDate)
        );
        Optional.of(wish.isNotice()).ifPresent(
                isNotice -> verifiedWish.setNotice(isNotice)
        );
        return wishRepository.save(verifiedWish);
    }

    private Wish verifyWishlist(long wishlistId) {
        Optional<Wish> optionalWishlist = wishRepository.findById(wishlistId);
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
//        checkWishListExist(memberFound, wish.getIsbn());

        wish.setMember(memberFound);
        return wishRepository.save(wish);
    }

    /**
     * 특정 책에 대한 위시가 있으면, 리뷰 작성 상태를 true로 변경한다.
     *
     * @param member
     * @param book
     */
    @Transactional
    public void changeToReviewed(Member member, Book book) {
        Optional<Wish> optionalWish = wishRepository.findByMemberAndBook(member, book);
        optionalWish.ifPresent((wish) -> {
            wish.changedToReviewed();
        });
    }

//    void checkWishListExist(Member member, String isbn) {
//        Optional<Wish> wishLists = wishRepository.findByMemberAndIsbn(member, isbn);
//        if(wishLists.isPresent()) {ㄹ
//            throw new BusinessLogicException(ExceptionCode.WISHLIST_EXISTS);
//        }
//    }


}
