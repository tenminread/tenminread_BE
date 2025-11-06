package com.tenminread.controller;

import com.tenminread.service.QuizService;
import com.tenminread.dto.QuizDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuizController {

  private final QuizService quizService;

  /**
   * 1. 퀴즈 풀기 (GET /api/v1/books/{bookId}/summary/{seq}/quizzes)
   */
  @GetMapping("/books/{bookId}/summary/{seq}/quizzes")
  public ResponseEntity<List<QuizDtos.SolveResponse>> getQuizzesForSummary(
    @PathVariable Integer bookId,
    @PathVariable Integer seq
  ) {
    List<QuizDtos.SolveResponse> response = quizService.getQuizzesForSummary(bookId, seq);
    return ResponseEntity.ok(response);
  }

  /**
   * 2. 퀴즈 세트 정답 제출 (POST /api/v1/books/{bookId}/summary/{seq}/submit)
   */
  @PostMapping("/books/{bookId}/summary/{seq}/submit")
  public ResponseEntity<QuizDtos.SubmitSetResponse> submitQuizSet(
    @PathVariable Integer bookId,
    @PathVariable Integer seq,
    @RequestBody QuizDtos.SubmitSetRequest request
    // ⬇️ (TODO: 추후 Spring Security 인증 기능으로 대체)
    // @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    // (1) ⬇️ (TODO: 추후 인증 기능이 완성되면 이 코드를 사용)
    // Integer userId = userDetails.getUserId();

    // (2) ⬇️ (지금 당장 테스트를 위한 임시 하드코딩)
    Integer testUserId = 1;

    QuizDtos.SubmitSetResponse response = quizService.submitQuizSet(bookId, seq, testUserId, request);
    return ResponseEntity.ok(response);
  }

  /**
   * 3. 퀴즈 복습 (GET /api/books/{bookId}/summary/{seq}/quizzes/{quizId}/review)
   */
  @GetMapping("/books/{bookId}/summary/{seq}/quizzes/{quizId}/review")
  public ResponseEntity<QuizDtos.ReviewResponse> getQuizReview(
    @PathVariable Integer bookId,    // ⬅️ 파라미터 추가
    @PathVariable Integer seq,       // ⬅️ 파라미터 추가
    @PathVariable Integer quizId
    // ⬇️ (TODO: 추후 인증 기능으로 대체)
    // @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    // (TODO: 추후 userId는 인증 토큰에서 가져오기)
    Integer testUserId = 1;

    // (TODO: bookId, seq, quizId가 서로 일치하는지 검증하는 로직을
    //  quizService.getQuizReview에 추가하면 더욱 안전합니다.)

    QuizDtos.ReviewResponse response = quizService.getQuizReview(quizId, testUserId);
    return ResponseEntity.ok(response);
  }
}
