package com.tenminread.repository;

import com.tenminread.domain.user.UserInterest;
import com.tenminread.domain.common.id.UserInterestId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// 유저의 관심분야 (세부관심분야 x)에 대한 선택 저장 및 조회
@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, UserInterestId> {

  @Query("select ui.id.categoryid from UserInterest ui where ui.id.userId = :userId")
  List<Integer> findCategoryIdsByUser(Integer userId);
  // 사용자가 선택한 categoryid(문학1, 역사2, 철학3, 사회4, 자연5, 기술6)를 조회

  void deleteByIdUserId(Integer userId); // 온보딩 전량 교체 등에 사용 가능
}
