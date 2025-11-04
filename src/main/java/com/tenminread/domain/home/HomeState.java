package com.tenminread.domain.home;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "HomeState")
public class HomeState {

  @Id
  @Column(name = "user_id", nullable = false)
  private Integer userId;

  @Column(name = "anchor_date", nullable = false)
  private LocalDate anchorDate;

  @Column(name = "current_bookid")
  private Integer currentBookid;

  @Column(name = "last_bookid")
  private Integer lastBookid; // 다음날 랜덤 선정 시, 직전의 오늘의 책 제외 용도

  @Column(name = "status", nullable = false, length = 16)
  private String status; // IDLE | READING , ReadingProgress 기반으로 계산, 완독 시 IDLE
}
