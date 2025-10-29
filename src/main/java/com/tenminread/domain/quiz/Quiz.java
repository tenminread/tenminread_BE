package com.tenminread.domain.quiz;

import com.tenminread.domain.summary.Summary;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Quiz",
  indexes = @Index(name = "idx_quiz_summary", columnList = "sumid")
)
public class Quiz {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "quizid", nullable = false)
  private Integer quizid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "sumid", referencedColumnName = "sumid",
    foreignKey = @ForeignKey(name = "fk_quiz_summary"))
  private Summary summary;

  @Column(name = "question", nullable = false, columnDefinition = "TEXT")
  private String question;

  @Column(name = "answer", nullable = false, columnDefinition = "TEXT")
  private String answer;

  @Column(name = "points", nullable = false)
  private Integer points = 1;
}
