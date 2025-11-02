package com.tenminread.bookdb.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "book_text")
public class BookText {

  @Id
  @Column(name = "bookid", nullable = false)
  private Integer bookid;

  @Lob
  @Column(name = "original_text", nullable = false, columnDefinition = "TEXT")
  private String originalText;

  @Column(name = "lang_code", nullable = false, length = 8)
  private String langCode;          // DB DEFAULT 'en'

  @Column(name = "version", nullable = false)
  private String version;           // DB DEFAULT 'v1'
}
