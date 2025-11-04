package com.tenminread.dto;

import java.util.List;

public class HomeDtos {
  public record BookCard(Integer bookid, String title, String author, Integer categoryId) {}
  // 부족한거 bookmeta로 추후 확장
  public record TodayProgress(Integer bookid, double progressPercent, int readDays, int totalDays) {}
  // 오늘의 책 진행률
  public record HomeTodayResponse(BookCard today, TodayProgress progress, String status) {}

  // 캐러셀 공통 응답(최대 5권), 앵커 문자열 포함
  public record CarouselResponse(List<BookCard> items, String anchorDate) {}
}
