package com.tenminread.dto; // (패키지는 예시입니다)

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FavoriteToggleResponse {
  private Integer bookId;
  private Integer userId;
  private boolean isFavorited;
}
