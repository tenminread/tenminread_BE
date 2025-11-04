package com.tenminread.repository;

import com.tenminread.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

  //  단건 상세 조회 시 카테고리까지 즉시 로딩
  @Query("""
           SELECT b
             FROM Book b
             JOIN FETCH b.category
            WHERE b.bookid = :bookId
           """)
  Optional<Book> findByIdWithCategory(@Param("bookId") Integer bookId);

  // 관심 카테고리 목록으로 도서 풀 조회 (카테고리 fetch join, DISTINCT)
  @Query("""
           SELECT DISTINCT b
             FROM Book b
             JOIN FETCH b.category
            WHERE b.category.categoryid IN :categoryIds
           """)
  List<Book> findAllByCategoryIds(@Param("categoryIds") List<Integer> categoryIds);
}
