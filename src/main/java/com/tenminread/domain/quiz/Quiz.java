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

  @Column(name = "answer", nullable = false, columnDefinition = "TEXT")
  private String answer;

  @Builder.Default
  @Column(name = "points", nullable = false)
  private Integer points = 1;
}
