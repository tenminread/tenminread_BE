package com.tenminread.controller;

import com.tenminread.dto.HomeDtos.HomeTodayResponse;
import com.tenminread.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

  private final HomeService homeService;

  /** KST 기준 오늘의 책(베타 플래그가 설정되면 1권 고정, 아니면 정상 추천) */
  @GetMapping("/today")
  public HomeTodayResponse today(@RequestAttribute("userId") Integer userId) {
    return homeService.getToday(userId);
  }
}
