package com.tenminread.domain.quiz;

import com.tenminread.domain.common.id.QuizRecordId;
import com.tenminread.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "QuizRecord",
  indexes = @Index(name = "idx_qr_quiz", columnList = "quizid"))
public class QuizRecord {
  @EmbeddedId
  private QuizRecordId id;

  @MapsId("userId")
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "userid",
    foreignKey = @ForeignKey(name = "fk_qr_user"))
  private User user;

  @MapsId("quizid")
  @ManyToOne(optional = false)
  @JoinColumn(name = "quizid", referencedColumnName = "quizid",
    foreignKey = @ForeignKey(name = "fk_qr_quiz"))
  private Quiz quiz;

  @Column(name = "score", nullable = false)
  private Integer score;

  @Column(name = "user_answer", nullable = false, columnDefinition = "TEXT")
  private String userAnswer;
}
