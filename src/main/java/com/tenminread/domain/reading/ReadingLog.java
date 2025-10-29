package com.tenminread.domain.reading;

import com.tenminread.domain.book.Book;
import com.tenminread.domain.category.Category;
import com.tenminread.domain.summary.Summary;
import com.tenminread.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "ReadingLog",
  indexes = {
    @Index(name = "idx_rl_user_date", columnList = "user_id,read_date"),
    @Index(name = "idx_rl_book", columnList = "bookid"),
    @Index(name = "idx_rl_sum", columnList = "sumid"),
    @Index(name = "idx_rl_category", columnList = "categoryid")
  }
)
public class ReadingLog {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "logid", nullable = false)
  private Long logid;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "userid",
    foreignKey = @ForeignKey(name = "fk_rl_user"))
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bookid", referencedColumnName = "bookid",
    foreignKey = @ForeignKey(name = "fk_rl_book"))
  private Book book;

  @ManyToOne(optional = true)
  @JoinColumn(name = "sumid", referencedColumnName = "sumid",
    foreignKey = @ForeignKey(name = "fk_rl_sum"))
  private Summary summary; // DB에서 ON DELETE SET NULL

  @ManyToOne(optional = false)
  @JoinColumn(name = "categoryid", referencedColumnName = "categoryid",
    foreignKey = @ForeignKey(name = "fk_rl_category"))
  private Category category;

  @Column(name = "read_date", nullable = false)
  private LocalDate readDate;
}
