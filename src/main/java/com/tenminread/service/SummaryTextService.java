package com.tenminread.service;

import com.tenminread.bookdb.domain.SummaryText;
import com.tenminread.bookdb.domain.SummaryTextId;
import com.tenminread.bookdb.repository.SummaryTextRepository;
import com.tenminread.exception.ResourceNotFoundException;
import com.tenminread.dto.BookDtos; // ⬅️ DTO 임포트 변경
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
// (중요!) 'bookdb'용 트랜잭션 매니저를 명시합니다.
@Transactional(readOnly = true, transactionManager = "bookDbTransactionManager")
public class SummaryTextService {

  private final SummaryTextRepository summaryTextRepository;

  /**
   * bookId와 seq로 특정 요약본 텍스트를 조회합니다.
   */
  public BookDtos.SummaryResponse findSummaryByBookIdAndSeq(Integer bookId, Integer seq) {
    SummaryTextId id = new SummaryTextId(bookId, seq);
    SummaryText summaryText = summaryTextRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException(
        "SummaryText not found with bookId: " + bookId + " and seq: " + seq
      ));
    return new BookDtos.SummaryResponse(summaryText);
  }
}
