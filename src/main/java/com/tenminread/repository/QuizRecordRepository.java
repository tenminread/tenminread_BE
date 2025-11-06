package com.tenminread.repository;

import com.tenminread.domain.common.id.QuizRecordId;
import com.tenminread.domain.quiz.QuizRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface QuizRecordRepository extends JpaRepository<QuizRecord, QuizRecordId> {

  // 복합키의 일부인 userId와 quizId로 레코드를 찾습니다.
  Optional<QuizRecord> findByIdUserIdAndIdQuizid(Integer userId, Integer quizid);
}
