package com.tenminread.domain.quiz;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Quiz",
  uniqueConstraints = {
    @UniqueConstraint(name = "uk_quiz_per_summary", columnNames = {"bookid", "seq", "quiz_no"})
  },
  indexes = {
    @Index(name = "idx_quiz_book_seq", columnList = "bookid, seq")
  }
)
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "quizid", nullable = false)
  private Integer quizid;

  // Summary FK 대신 자연키(bookid, seq) 채택 -> bookdb의 SummaryText와 동일
  @Column(name = "bookid", nullable = false)
  private Integer bookid;

  @Column(name = "seq", nullable = false)
  private Integer seq;

  // 요약 단위 내 문항 번호(1..5)
  @Column(name = "quiz_no", nullable = false)
  private Short quizNo;

  @Column(name = "question", nullable = false, columnDefinition = "TEXT")
  private String question;

  @Column(name = "choice1", nullable = false, columnDefinition = "TEXT")
  private String choice1; // 1번 선택지

  @Column(name = "choice2", nullable = false, columnDefinition = "TEXT")
  private String choice2; // 2번 선택지

  @Column(name = "choice3", nullable = false, columnDefinition = "TEXT")
  private String choice3; // 3번 선택지

  @Column(name = "choice4", nullable = false, columnDefinition = "TEXT")
  private String choice4; // 4번 선택지

  @Column(name = "correct_choice", nullable = false)
  private Integer correctChoice; // 정답 번호 (1, 2, 3, 4)

  @Builder.Default
  @Column(name = "points", nullable = false)
  private Integer points = 1;
}
