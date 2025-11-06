package com.tenminread.dto;

import com.tenminread.bookdb.domain.SummaryText;
import com.tenminread.domain.book.Book;
import com.tenminread.domain.bookmeta.BookMeta;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 책(Book) 관련 DTO 클래스들을 모아두는 컨테이너 클래스
 * (Nested Static Classes 방식)
 */
public class BookDtos {

  /**
   * 책 소개 응답 (GET /books/{bookId})
   * (제목, 후킹문구, 책요약, 작가소개)
   */
  @Getter
  public static class IntroResponse {
    private Integer bookId;
    private String title;
    private String hook25Char;
    private String bookIntro;
    private String authorBio;

    public IntroResponse(Book book, BookMeta bookMeta) {
      this.bookId = book.getBookid();
      this.title = book.getTitle();
      this.hook25Char = bookMeta.getHook25char();
      this.bookIntro = bookMeta.getBookIntro();
      this.authorBio = bookMeta.getAuthorBio();
    }
  }

  /**
   * 책 목차 응답 (GET /books/{bookId}/index)
   */
  @Getter
  public static class IndexResponse {
    private Integer bookId;
    private String toc;

    public IndexResponse(BookMeta bookMeta) {
      this.bookId = bookMeta.getBookid();
      this.toc = bookMeta.getToc();
    }
  }

  /**
   * 요약본 응답 (GET /books/{bookId}/summary/{seq})
   */
  @Getter
  public static class SummaryResponse {
    private Integer bookId;
    private Integer seq;
    private String summaryText;
    private String version;

    public SummaryResponse(SummaryText summaryText) {
      this.bookId = summaryText.getBookid();
      this.seq = summaryText.getSeq();
      this.summaryText = summaryText.getSummaryText();
      this.version = summaryText.getVersion();
    }
  }

  /**
   * 좋아요 토글 응답 (POST /books/{bookId}/favorites)
   */
  @Getter
  @AllArgsConstructor
  public static class FavoriteToggleResponse {
    private Integer bookId;
    private Integer userId;
    private boolean isFavorited;
  }
}
