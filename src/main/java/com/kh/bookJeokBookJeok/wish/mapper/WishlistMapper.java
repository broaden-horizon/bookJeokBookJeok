package com.kh.bookJeokBookJeok.wish.mapper;

import com.kh.bookJeokBookJeok.book.entity.Book;
import com.kh.bookJeokBookJeok.wish.dto.WishDto;
import com.kh.bookJeokBookJeok.wish.entity.Wish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WishlistMapper {
  default Wish postToWish(WishDto.Post post) {
    return Wish.builder()
        .book(postToBook(post))
        .dueDate(post.getDueDate())
        .build();
  }

  Book postToBook(WishDto.Post post);
  Wish wishPatchToWish(WishDto.Patch patch);

  WishDto.Response wishToResponse(Wish wish);
}
