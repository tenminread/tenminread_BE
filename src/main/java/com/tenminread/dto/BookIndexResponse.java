package com.tenminread.dto;

import com.tenminread.domain.bookmeta.BookMeta;
import lombok.Getter;

@Getter
public class BookIndexResponse {

  private Integer bookId;
  private String toc;

  public BookIndexResponse(BookMeta bookMeta) {
    this.bookId = bookMeta.getBookid();
    this.toc = bookMeta.getToc();
  }
}
