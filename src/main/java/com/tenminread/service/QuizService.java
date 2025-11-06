package com.tenminread.service;

import com.tenminread.domain.common.id.QuizRecordId;
import com.tenminread.domain.quiz.Quiz;
import com.tenminread.domain.quiz.QuizRecord;
import com.tenminread.repository.QuizRecordRepository;
import com.tenminread.repository.QuizRepository;
import com.tenminread.repository.UserRepository;
import com.tenminread.domain.user.User;
import com.tenminread.exception.ResourceNotFoundException;
import com.tenminread.dto.QuizDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // (Main DB 트랜잭션)
public class QuizService {

  private final QuizRepository quizRepository;
  private final QuizRecordRepository quizRecordRepository;
  private final UserRepository userRepository;

  /**
   * 1. 퀴즈 풀기 (요약본의 퀴즈 목록 조회)
   */
  public List<QuizDtos.SolveResponse> getQuizzesForSummary(Integer bookId, Integer seq) {
    List<Quiz> quizzes = quizRepository.findByBookidAndSeqOrderByQuizNoAsc(bookId, seq);
    if (quizzes.isEmpty()) {
      throw new ResourceNotFoundException("Quizzes not found for bookId: " + bookId + " and seq: " + seq);
    }
    return quizzes.stream()
      .map(QuizDtos.SolveResponse::new)
      .collect(Collectors.toList());
  }

  /**
   * 2. 퀴즈 세트 정답 제출 (!!! Transactional !!!)
   */
  @Transactional // (DB 쓰기 작업, All-or-Nothing)
  public QuizDtos.SubmitSetResponse submitQuizSet(Integer bookId, Integer seq, Integer userId, QuizDtos.SubmitSetRequest request) {

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

    // 1. 제출된 퀴즈ID 목록으로 실제 퀴즈 정보(정답 포함)를 DB에서 조회
    List<Integer> quizIds = request.getAnswers().stream()
      .map(QuizDtos.SubmitSetRequest.SingleAnswer::getQuizId)
      .toList();

    // 퀴즈ID를 Key로 하는 Map 생성 (정답 채점 시 조회를 빠르게)
    Map<Integer, Quiz> quizAnswerMap = quizRepository.findAllById(quizIds).stream()
      .collect(Collectors.toMap(Quiz::getQuizid, quiz -> quiz));

    int correctCount = 0;
    List<QuizRecord> recordsToSave = new ArrayList<>();
    List<QuizDtos.ResultDetail> results = new ArrayList<>();

    // 2. 퀴즈 채점 및 QuizRecord 생성 (아직 DB에 저장 안 함)
    for (QuizDtos.SubmitSetRequest.SingleAnswer answer : request.getAnswers()) {
      Quiz quiz = quizAnswerMap.get(answer.getQuizId());
      if (quiz == null) {
        throw new ResourceNotFoundException("Quiz not found during submit: " + answer.getQuizId());
      }

      boolean isCorrect = quiz.getCorrectChoice().equals(answer.getSubmittedAnswer());
      if (isCorrect) {
        correctCount++;
      }
      int score = isCorrect ? quiz.getPoints() : 0;

      QuizRecordId recordId = new QuizRecordId(userId, quiz.getQuizid());

      QuizRecord record = QuizRecord.builder()
        .id(recordId)
        .user(user)
        .quiz(quiz)
        .score(score)
        .userAnswer(String.valueOf(answer.getSubmittedAnswer()))
        .build();

      recordsToSave.add(record);
      results.add(new QuizDtos.ResultDetail(quiz, isCorrect));
    }

    // 3. (!!!) 모든 레코드를 단일 트랜잭션으로 DB에 저장 (saveAll)
    // 여기서 실패하면 롤백되므로 아무것도 저장되지 않습니다.
    quizRecordRepository.saveAll(recordsToSave);

    // 4. 최종 결과 반환
    return new QuizDtos.SubmitSetResponse(bookId, seq, request.getAnswers().size(), correctCount, results);
  }

  /**
   * 3. 퀴즈 복습 (제출 기록 조회)
   */
  public QuizDtos.ReviewResponse getQuizReview(Integer quizId, Integer userId) {
    Quiz quiz = quizRepository.findById(quizId)
      .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

    QuizRecord record = quizRecordRepository.findByIdUserIdAndIdQuizid(userId, quizId)
      .orElseThrow(() -> new ResourceNotFoundException("QuizRecord not found for user: " + userId + " quiz: " + quizId));

    return new QuizDtos.ReviewResponse(quiz, record);
  }
}
