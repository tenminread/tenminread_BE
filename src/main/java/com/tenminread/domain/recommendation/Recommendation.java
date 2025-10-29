package com.tenminread.domain.recommendation;

import com.tenminread.domain.book.Book;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Recommendation",
  indexes = @Index(name = "idx_reco_book", columnList = "bookid")
)
public class Recommendation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "recid", nullable = false)
  private Integer recid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bookid", referencedColumnName = "bookid",
    foreignKey = @ForeignKey(name = "fk_reco_book"))
  private Book book;
}
