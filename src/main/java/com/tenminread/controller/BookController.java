package com.tenminread.controller; // (패키지는 예시입니다)

import com.tenminread.service.BookService;
import com.tenminread.service.FavoriteService;
import com.tenminread.service.SummaryTextService;
import com.tenminread.dto.BookDtos; // ⬅️ 통합 DTO 임포트
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book") // API 버전을 명시하는 것을 권장합니다.
@RequiredArgsConstructor
public class BookController {

  // (Main DB)
  private final BookService bookService;
  private final FavoriteService favoriteService;

  // (BookDB)
  private final SummaryTextService summaryTextService;

  /**
   * 1. 책 소개 API (제목, 후킹문구, 책요약, 작가소개)
   */
  @GetMapping("/{bookId}")
  public ResponseEntity<BookDtos.IntroResponse> getBookIntro(
    @PathVariable Integer bookId
  ) {
    BookDtos.IntroResponse response = bookService.findBookIntroById(bookId);
    return ResponseEntity.ok(response);
  }

  /**
   * 2. 책 목차 API
   */
  @GetMapping("/{bookId}/index")
  public ResponseEntity<BookDtos.IndexResponse> getBookIndex(
    @PathVariable Integer bookId
  ) {
    BookDtos.IndexResponse response = bookService.findBookIndexById(bookId);
    return ResponseEntity.ok(response);
  }

  /**
   * 3. 좋아요 토글 API
   */
  @PostMapping("/{bookId}/favorites")
  public ResponseEntity<BookDtos.FavoriteToggleResponse> toggleFavorite(
    @PathVariable Integer bookId
    // ⬇️ (TODO: 추후 Spring Security 인증 기능으로 대체)
    // @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    // (1) ⬇️ (TODO: 추후 인증 기능이 완성되면 이 코드를 사용)
    // Integer userId = userDetails.getUserId(); // ⬅️ 인증된 토큰에서 ID를 꺼냄

    // (2) ⬇️ (지금 당장 테스트를 위한 임시 하드코딩)
    Integer testUserId = 1; // ⬅️ 1번 유저가 요청했다고 가정

    BookDtos.FavoriteToggleResponse response = favoriteService.toggleFavorite(testUserId, bookId);
    return ResponseEntity.ok(response);
  }

  /**
   * 4. 특정 요약본 조회 API (bookdb)
   */
  @GetMapping("/{bookId}/summary/{seq}")
  public ResponseEntity<BookDtos.SummaryResponse> getBookSummaryBySeq(
    @PathVariable Integer bookId,
    @PathVariable Integer seq
  ) {
    BookDtos.SummaryResponse response = summaryTextService.findSummaryByBookIdAndSeq(bookId, seq);
    return ResponseEntity.ok(response);
  }
}
