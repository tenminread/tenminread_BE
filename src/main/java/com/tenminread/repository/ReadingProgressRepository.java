package com.tenminread.repository;

import com.tenminread.domain.reading.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReadingProgressRepository extends JpaRepository<ReadingProgress, Long> {

  List<ReadingProgress> findByUserIdAndBookidOrderBySeqAsc(Integer userId, Integer bookid);
}
