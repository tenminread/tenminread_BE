package com.tenminread.dto; // (패키지는 예시입니다)

import com.tenminread.domain.book.Book;
import com.tenminread.domain.bookmeta.BookMeta;
import lombok.Getter;

@Getter
public class BookIntroResponse {

  private Integer bookId;
  private String title;       // 1. 책 제목
  private String hook25Char;  // 2. 후킹 문구
  private String bookIntro;   // 3. 책 전체 요약(소개)
  private String authorBio;   // 4. 작가 소개

  public BookIntroResponse(Book book, BookMeta bookMeta) {
    this.bookId = book.getBookid();
    this.title = book.getTitle();
    this.hook25Char = bookMeta.getHook25char();
    this.bookIntro = bookMeta.getBookIntro();
    this.authorBio = bookMeta.getAuthorBio();
  }
}
