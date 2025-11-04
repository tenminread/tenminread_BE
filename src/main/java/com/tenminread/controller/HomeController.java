package com.tenminread.controller;

import com.tenminread.dto.HomeDtos.CarouselResponse;
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

  /** 관심 캐러셀(관심 대분야에서 Today 제외, 5권, 앵커 고정) */
  @GetMapping("/interest")
  public CarouselResponse interest(@RequestAttribute("userId") Integer userId) {
    return homeService.getInterestCarousel(userId);
  }

  /** 부족 캐러셀(비관심 대분야에서 5권, 앵커 고정) */
  @GetMapping("/deficit")
  public CarouselResponse deficit(@RequestAttribute("userId") Integer userId) {
    return homeService.getDeficitCarousel(userId);
  }
}
