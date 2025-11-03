package com.tenminread.controller; // (패키지는 예시입니다)

import com.tenminread.dto.BookIntroResponse;
import com.tenminread.dto.BookIndexResponse;
import com.tenminread.service.BookService;
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

  private final BookService bookService;

  @GetMapping("/{bookId}")
  public ResponseEntity<BookIntroResponse> getBookInfo(@PathVariable Integer bookId) {
    BookIntroResponse response = bookService.findBookIntroById(bookId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{bookId}/index")
  public ResponseEntity<BookIndexResponse> getBookIndex(@PathVariable Integer bookId) {
    BookIndexResponse response = bookService.findBookIndexById(bookId);
    return ResponseEntity.ok(response);
  }
}
