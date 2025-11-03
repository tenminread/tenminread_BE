package com.tenminread.service; // (패키지는 예시입니다)

import com.tenminread.domain.book.Book;
import com.tenminread.domain.bookmeta.BookMeta;
import com.tenminread.repository.BookMetaRepository;
import com.tenminread.repository.BookRepository;
import com.tenminread.exception.ResourceNotFoundException;
import com.tenminread.dto.BookInfoResponse; // (이전 단계에서 만든 DTO)
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 주입
@Transactional(readOnly = true) // CQS 원칙에 따라 조회 메서드는 readOnly=true
public class BookService {

  private final BookRepository bookRepository;
  private final BookMetaRepository bookMetaRepository;

  /**
   * bookId로 책의 기본 정보와 메타 정보를 조회합니다.
   * @param bookId
   * @return BookInfoResponse DTO
   */
  public BookInfoResponse findBookInfoById(Integer bookId) {

    // 1. Book 엔티티 조회 (Category 정보 포함)
    Book book = bookRepository.findByIdWithCategory(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));

    // 2. BookMeta 엔티티 조회
    BookMeta bookMeta = bookMetaRepository.findById(bookId)
      .orElseThrow(() -> new ResourceNotFoundException("BookMeta not found with id: " + bookId));

    // 3. Category 이름 조회
    String categoryName = book.getCategory().getCategoryName();

    // 4. DTO로 변환하여 반환
    return new BookInfoResponse(book, bookMeta, categoryName);
  }
}
