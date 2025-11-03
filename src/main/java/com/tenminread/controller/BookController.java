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
import com.tenminread.service.FavoriteService;
import com.tenminread.dto.FavoriteToggleResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;
  private final FavoriteService favoriteService;

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

  @PostMapping("/{bookId}/favorites")
  public ResponseEntity<FavoriteToggleResponse> toggleFavorite(
    @PathVariable Integer bookId
    // ⬇️ @RequestParam Integer userId  <-- (보안 취약점이므로 삭제!)

    // ⬇️ (TODO: 추후 Spring Security 도입 시 이 코드로 변경)
    // @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {

    // (1) ⬇️ (TODO: 추후 인증 기능이 완성되면 이 코드를 사용)
    // Integer userId = userDetails.getUserId(); // ⬅️ 인증된 토큰에서 ID를 꺼냄

    // (2) ⬇️ (지금 당장 테스트를 위한 임시 하드코딩)
    Integer testUserId = 1; // ⬅️ 1번 유저가 요청했다고 가정

    FavoriteToggleResponse response = favoriteService.toggleFavorite(testUserId, bookId);
    return ResponseEntity.ok(response);
  }
}
