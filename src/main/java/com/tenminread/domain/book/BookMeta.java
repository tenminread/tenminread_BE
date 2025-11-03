package com.tenminread.domain.bookmeta;

import com.tenminread.domain.book.Book;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "BookMeta")
public class BookMeta {

  @Id
  @Column(name = "bookid", nullable = false)
  private Integer bookid;

  @OneToOne(optional = false, fetch = FetchType.LAZY)
  @MapsId
  @JoinColumn(
    name = "bookid",
    referencedColumnName = "bookid",
    foreignKey = @ForeignKey(name = "fk_bookmeta_book")
  )
  private Book book;

  @Column(name = "book_intro", columnDefinition = "TEXT", nullable = false)
  private String bookIntro;    // 책 전체 소개(고정 제공)

  @Column(name = "author_bio", columnDefinition = "TEXT", nullable = false)
  private String authorBio;    // 작가 소개(고정 제공)

  @Column(name = "toc", columnDefinition = "TEXT", nullable = false)
  private String toc;          // 목차(고정 제공)

  @Column(name = "hook_25char", length = 30, nullable = false)
  private String hook25char;   // 서비스단 25자 검증
}
