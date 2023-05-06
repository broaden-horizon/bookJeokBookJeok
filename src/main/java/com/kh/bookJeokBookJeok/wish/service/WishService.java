package com.kh.bookJeokBookJeok.wish.service;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.service.BookService;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;
    private final AuthenticationUtils authenticationUtils;
    private final MemberService memberService;
    private final BookService bookService;

    public Wish getWish(long wishId) {
//        //토큰으로부터 멤버 추출
//        Member memberFound = memberService.verifyMemberFromToken();
//        //해당 멤버가 작성한 위시리스트가 맞는지 확인
//        Wish verifiedWish = findVerifyWish(wishId, memberFound.getMemberId());
//
//        return verifiedWish;
        return null;
    }

    /**
     * 위시의 due date 수정
     * @param wish
     * @param wishId
     * @param memberId
     * @return
     */
    @Transactional
    public Wish update(Wish wish, Long wishId, Long memberId) {
        Member member = memberService.findVerifiedMember(memberId);
        Wish verifiedWish = findVerifyWish(wishId,member);
        verifiedWish.setDueDate(wish.getDueDate());
        return verifiedWish;
    }

    private Wish findVerifyWish(long wishId) {
        Optional<Wish> optionalWishlist = wishRepository.findById(wishId);
        return optionalWishlist.orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISH_NOT_FOUND));
    }
    private Wish findVerifyWish(long wishId, Member member) {
        Optional<Wish> optionalWish = wishRepository.findByWishIdAndMember(wishId, member);
        return optionalWish.orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISH_NOT_FOUND));
    }

    /**
     * 위시를 생성합니다.
     *
     * 읽고자하는 책이 DB에 없다면 추가로 생성합니다.
     *
     * @param wish
     * @param memberId
     * @return
     */
    @Transactional
    public Wish create(Wish wish, Long memberId) {
        Member member = memberService.findVerifiedMember(memberId);
        Book book = bookService.createIfAbsent(wish.getBook());
        wish.setMember(member);
        wish.setBook(book);
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
