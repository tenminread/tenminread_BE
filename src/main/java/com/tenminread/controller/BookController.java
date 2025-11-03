package com.tenminread.controller; // (패키지는 예시입니다)

import com.tenminread.dto.BookInfoResponse;
import com.tenminread.service.BookService; // (Service 레이어가 필요합니다)
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService; // (Service 주입)

  @GetMapping("/{bookId}")
  public ResponseEntity<BookInfoResponse> getBookInfo(@PathVariable Integer bookId) {
    // BookService는 내부적으로 BookRepository와 BookMetaRepository를
    // 모두 사용하여 DTO를 조립합니다.
    BookInfoResponse response = bookService.findBookInfoById(bookId);
    return ResponseEntity.ok(response);
  }
}
