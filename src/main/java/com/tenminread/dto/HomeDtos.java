package com.tenminread.dto;

public class HomeDtos {
  public record BookCard(Integer bookid, String title, String author, Integer categoryId) {}
  // 부족한거 bookmeta로 추후 확장
  public record TodayProgress(Integer bookid, double progressPercent, int readDays, int totalDays) {}
  // 오늘의 책 진행률
  public record HomeTodayResponse(BookCard today, TodayProgress progress, String status) {}
}
