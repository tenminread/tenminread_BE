package com.tenminread.bookdb.repository;

import com.tenminread.bookdb.domain.BookText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookTextRepository extends JpaRepository<BookText, Integer> { }
