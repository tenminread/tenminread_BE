package com.tenminread.bookdb.repository;

import com.tenminread.bookdb.domain.SummaryText;
import com.tenminread.bookdb.domain.SummaryTextId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SummaryTextRepository extends JpaRepository<SummaryText, SummaryTextId> {
  List<SummaryText> findByIdBookidOrderByIdSeqAsc(Integer bookid);
}
