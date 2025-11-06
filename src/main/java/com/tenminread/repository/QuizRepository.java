package com.tenminread.repository;

import com.tenminread.domain.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

  // bookId와 seq로 퀴즈 목록을 찾습니다 (quizNo 순으로 정렬)
  List<Quiz> findByBookidAndSeqOrderByQuizNoAsc(Integer bookid, Integer seq);
}
