package com.tenminread.repository; // (패키지는 예시입니다)

import com.tenminread.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

  // N+1 문제를 피하기 위해 category를 함께 fetch join 합니다.
  @Query("SELECT b FROM Book b JOIN FETCH b.category WHERE b.bookid = :bookId")
  Optional<Book> findByIdWithCategory(@Param("bookId") Integer bookId);
}
