package com.tenminread.repository; // (패키지는 예시입니다)

import com.tenminread.domain.bookmeta.BookMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookMetaRepository extends JpaRepository<BookMeta, Integer> {
  // BookMeta의 ID는 Book의 ID와 동일하므로 기본 findById를 사용하면 됩니다.
}
