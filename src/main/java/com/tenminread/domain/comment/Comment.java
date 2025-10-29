package com.tenminread.domain.comment;

import com.tenminread.domain.book.Book;
import com.tenminread.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Comment",
  indexes = {
    @Index(name = "idx_comment_book", columnList = "bookid"),
    @Index(name = "idx_comment_user", columnList = "user_id")
  }
)
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "commentId", nullable = false)
  private Integer commentId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "bookid", referencedColumnName = "bookid",
    foreignKey = @ForeignKey(name = "fk_comment_book"))
  private Book book;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "userid",
    foreignKey = @ForeignKey(name = "fk_comment_user"))
  private User user;

  @Column(name = "comment", nullable = false, columnDefinition = "TEXT")
  private String comment;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @PrePersist
  void prePersist() {
    if (createdAt == null) createdAt = LocalDateTime.now();
  }
}
