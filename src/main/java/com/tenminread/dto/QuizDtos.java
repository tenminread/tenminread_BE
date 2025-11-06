package com.tenminread.dto;

import com.tenminread.domain.quiz.Quiz;
import com.tenminread.domain.quiz.QuizRecord;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 퀴즈 관련 DTO
 */
public class QuizDtos {

  /**
   * 1. 퀴즈 풀기 (GET) 응답 DTO
   * (문제, 선택지 4개. 정답은 제외)
   */
  @Getter
  public static class SolveResponse {
    private Integer quizId;
    private Short quizNo;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    public SolveResponse(Quiz quiz) {
      this.quizId = quiz.getQuizid();
      this.quizNo = quiz.getQuizNo();
      this.question = quiz.getQuestion();
      this.choice1 = quiz.getChoice1();
      this.choice2 = quiz.getChoice2();
      this.choice3 = quiz.getChoice3();
      this.choice4 = quiz.getChoice4();
    }
  }

  /**
   * 2. 퀴즈 세트 제출 (POST) 요청 DTO
   * (클라이언트가 푼 퀴즈 답안 목록)
   */
  @Getter
  @NoArgsConstructor // (JSON 역직렬화를 위해 필요)
  public static class SubmitSetRequest {
    private List<SingleAnswer> answers;

    @Getter
    @NoArgsConstructor
    public static class SingleAnswer {
      private Integer quizId;
      private Integer submittedAnswer; // 1, 2, 3, 4
    }
  }

  /**
   * 3. 퀴즈 세트 제출 (POST) 응답 DTO
   * (요청하신 "푼 퀴즈 개수 중 몇 개 맞았는지")
   */
  @Getter
  public static class SubmitSetResponse {
    private Integer bookId;
    private Integer seq;
    private Integer totalQuizzes;   // 푼 퀴즈 개수
    private Integer correctCount;   // 맞은 개수
    private List<ResultDetail> results; // 퀴즈별 채점 결과

    public SubmitSetResponse(Integer bookId, Integer seq, int total, int correct, List<ResultDetail> results) {
      this.bookId = bookId;
      this.seq = seq;
      this.totalQuizzes = total;
      this.correctCount = correct;
      this.results = results;
    }
  }

  /**
   * 3-1. 퀴즈 세트 제출 응답에 포함될 '개별 채점 결과' DTO
   */
  @Getter
  public static class ResultDetail {
    private Integer quizId;
    private boolean isCorrect;
    private Integer correctChoice; // 실제 정답

    public ResultDetail(Quiz quiz, boolean isCorrect) {
      this.quizId = quiz.getQuizid();
      this.isCorrect = isCorrect;
      this.correctChoice = quiz.getCorrectChoice();
    }
  }

  /**
   * 4. 퀴즈 복습 (GET) 응답 DTO
   * (문제, 선택지, 내 답, 정답, 정답유무. 해설 제외)
   */
  @Getter
  public static class ReviewResponse {
    // 문제 정보
    private Integer quizId;
    private String question;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;

    // 정답 정보
    private Integer correctChoice;

    // 제출 기록
    private Integer userAnswer;
    private boolean isCorrect;
    private Integer score;

    public ReviewResponse(Quiz quiz, QuizRecord record) {
      this.quizId = quiz.getQuizid();
      this.question = quiz.getQuestion();
      this.choice1 = quiz.getChoice1();
      this.choice2 = quiz.getChoice2();
      this.choice3 = quiz.getChoice3();
      this.choice4 = quiz.getChoice4();
      this.correctChoice = quiz.getCorrectChoice();

      this.userAnswer = Integer.parseInt(record.getUserAnswer());
      this.score = record.getScore();
      this.isCorrect = record.getScore() > 0;
    }
  }
}
