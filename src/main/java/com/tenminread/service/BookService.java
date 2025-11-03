package com.tenminread.service; // (패키지는 예시입니다)

import com.tenminread.domain.book.Book;
import com.tenminread.domain.bookmeta.BookMeta;
import com.tenminread.repository.BookMetaRepository;
import com.tenminread.repository.BookRepository;
import com.tenminread.exception.ResourceNotFoundException;
import com.tenminread.dto.BookIntroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

  private final BookRepository bookRepository;
  private final BookMetaRepository bookMetaRepository;

  public BookIntroResponse findBookIntroById(Integer bookId) {

    // 1. Book 엔티티 조회 (Title을 위해)
    Book book = bookRepository.findById(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

    // 2. BookMeta 엔티티 조회 (나머지 3개 정보를 위해)
    BookMeta bookMeta = bookMetaRepository.findById(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("BookMeta not found with id: " + bookId));

    // 3. 새 DTO로 변환하여 반환
    return new BookIntroResponse(book, bookMeta);
  }
}
