package com.tenminread.repository;

import com.tenminread.domain.summary.Summary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRepository extends JpaRepository<Summary, Integer> {

  // (bookid, seq) 수를 통한 분량 계산
  @Query("select s from Summary s where s.book.bookid = :bookid order by s.seq asc")
  List<Summary> findAllByBookidOrderBySeq(Integer bookid);
}
