package com.tenminread.domain.reading;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "ReadingProgress",
  uniqueConstraints = {
    @UniqueConstraint(name = "uk_progress_user_book_seq", columnNames = {"user_id", "bookid", "seq"})
  },
  indexes = @Index(name = "idx_progress_user", columnList = "user_id")
)
public class ReadingProgress {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Integer userId;

  @Column(name = "bookid", nullable = false)
  private Integer bookid;

  @Column(name = "seq", nullable = false)
  private Integer seq;

  // 0.0 ~ 100.0 (%). 프론트에서 0~100까지 소수로 보내면 그대로 저장
  @Column(name = "progress", nullable = false)
  private Double progress;
}
