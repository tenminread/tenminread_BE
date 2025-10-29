package com.tenminread.domain.book;

import com.tenminread.domain.category.Category;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Book",
  indexes = @Index(name = "idx_book_category", columnList = "categoryid")
)
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bookid", nullable = false)
  private Integer bookid;

  @Column(name = "title", nullable = false, length = 255)
  private String title;

  @Column(name = "author", length = 255)
  private String author;

  @Column(name = "introduction", columnDefinition = "TEXT")
  private String introduction;

  // DDL: `index` VARCHAR(255) → 자바 필드명은 indexText로, 컬럼 매핑은 "index"
  @Column(name = "index", length = 255)
  private String indexText;

  @ManyToOne(optional = false)
  @JoinColumn(name = "categoryid", referencedColumnName = "categoryid",
    foreignKey = @ForeignKey(name = "fk_book_category"))
  private Category category;
}
