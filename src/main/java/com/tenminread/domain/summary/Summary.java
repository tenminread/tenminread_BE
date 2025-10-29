package com.tenminread.domain.summary;

import com.tenminread.domain.book.Book;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Summary",
  indexes = @Index(name = "idx_summary_book", columnList = "bookid"),
  uniqueConstraints = @UniqueConstraint(name = "uk_summary_book_seq", columnNames = {"bookid","seq"})
)
public class Summary {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "sumid", nullable = false)
  private Integer sumid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bookid", referencedColumnName = "bookid",
    foreignKey = @ForeignKey(name = "fk_summary_book"))
  private Book book;

  @Column(name = "hook", length = 255)
  private String hook;

  @Column(name = "summary", columnDefinition = "TEXT")
  private String summary;

  @Column(name = "seq", nullable = false)
  private Integer seq;
}
