package com.tenminread.dto;

import com.tenminread.domain.book.Book;
import com.tenminread.domain.bookmeta.BookMeta; // BookMeta의 패키지
import lombok.Getter;

@Getter
public class BookInfoResponse {

  private Integer bookId;
  private String title;
  private String author;
  private String categoryName;
  private String hook25Char;
  private String bookIntro;
  private String authorBio;
  private String toc;

  // Service 레이어에서 Book, BookMeta, CategoryName을 조합하여 이 DTO를 생성
  public BookInfoResponse(Book book, BookMeta bookMeta, String categoryName) {
    this.bookId = book.getBookid();
    this.title = book.getTitle();
    this.author = book.getAuthor();
    this.categoryName = categoryName; // Category 엔티티에서 조회

    this.hook25Char = bookMeta.getHook25char();
    this.bookIntro = bookMeta.getBookIntro();
    this.authorBio = bookMeta.getAuthorBio();
    this.toc = bookMeta.getToc();
  }
}
