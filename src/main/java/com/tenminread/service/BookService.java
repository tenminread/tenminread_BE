package com.tenminread.service;

import com.tenminread.domain.book.Book;
import com.tenminread.domain.bookmeta.BookMeta;
import com.tenminread.repository.BookMetaRepository;
import com.tenminread.repository.BookRepository;
import com.tenminread.exception.ResourceNotFoundException;
import com.tenminread.dto.BookDtos; // ⬅️ DTO 임포트 변경
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // (Main DB 트랜잭션 사용)
public class BookService {

  private final BookRepository bookRepository;
  private final BookMetaRepository bookMetaRepository;

  /**
   * 책 소개 정보 조회
   */
  public BookDtos.IntroResponse findBookIntroById(Integer bookId) {
    // 1. Book 엔티티 조회 (Title을 위해)
    Book book = bookRepository.findById(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

    // 2. BookMeta 엔티티 조회 (나머지 정보를 위해)
    BookMeta bookMeta = bookMetaRepository.findById(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("BookMeta not found with id: " + bookId));

    // 3. DTO로 변환
    return new BookDtos.IntroResponse(book, bookMeta);
  }

  /**
   * 책 목차 정보 조회
   */
  public BookDtos.IndexResponse findBookIndexById(Integer bookId) {
    // 1. BookMeta 엔티티 조회 (toc 필드를 위해)
    BookMeta bookMeta = bookMetaRepository.findById(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("BookMeta not found with id: " + bookId));

    // 2. DTO로 변환
    return new BookDtos.IndexResponse(bookMeta);
  }
}
