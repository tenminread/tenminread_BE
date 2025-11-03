package com.tenminread.repository;

import com.tenminread.domain.bookmeta.BookMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMetaRepository extends JpaRepository<BookMeta, Integer> {
}
