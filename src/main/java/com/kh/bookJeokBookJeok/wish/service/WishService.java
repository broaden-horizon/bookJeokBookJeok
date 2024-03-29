package com.kh.bookJeokBookJeok.wish.service;

import com.kh.bookJeokBookJeok.authentication.AuthenticationUtils;
import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.book.service.BookService;
import com.kh.bookJeokBookJeok.exception.BusinessLogicException;
import com.kh.bookJeokBookJeok.exception.ExceptionCode;
import com.kh.bookJeokBookJeok.member.entity.Member;
import com.kh.bookJeokBookJeok.member.service.MemberService;
import com.kh.bookJeokBookJeok.status.GeneralStatus;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import com.kh.bookJeokBookJeok.wish.repository.WishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishService {
    private final WishRepository wishRepository;
    private final MemberService memberService;
    private final BookService bookService;

    /**
     * 본인이 작성한 특정 위시 조회
     *
     * @param wishId
     * @param memberId
     * @return Wish 엔티티
     */
    @Transactional(readOnly = true)
    public Wish retrieve(Long wishId, Long memberId) {
        Wish verifiedWish = findVerifyWish(wishId, memberId);
        return verifiedWish;
    }

    /**
     * 본인이 작성한 모든 위시 조회
     *
     * @param memberId
     * @return
     */
    @Transactional(readOnly = true)
    public Page<Wish> retrieveAll(Long memberId, Pageable pageable) {
        Page<Wish> wishes = wishRepository.findAllByMemberId(memberId, pageable);
        return wishes;
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

    /**
     * 위시의 상태를 DELETED로 변경합니다.
     *
     * @param wishId
     * @param memberId
     */
    @Transactional
    public void delete(Long wishId, Long memberId) {
        Wish verifiedWish = findVerifyWish(wishId, memberId);
        verifiedWish.setStatus(GeneralStatus.DELETED);
    }

    private Wish findVerifyWish(Long wishId) {
        Optional<Wish> optionalWishlist = wishRepository.findById(wishId);
        return optionalWishlist.orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISH_NOT_FOUND));
    }
    private Wish findVerifyWish(Long wishId, Member member) {
        Optional<Wish> optionalWish = wishRepository.findByWishIdAndMember(wishId, member);
        return optionalWish.orElseThrow(() -> new BusinessLogicException(ExceptionCode.WISH_NOT_FOUND));
    }

    private Wish findVerifyWish(Long wishId, Long memberId) {
        Optional<Wish> optionalWish = wishRepository.findByWishIdAndMemberId(wishId, memberId);
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
}
