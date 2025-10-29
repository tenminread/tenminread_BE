package com.tenminread.domain.favorite;

import com.tenminread.domain.book.Book;
import com.tenminread.domain.common.id.FavoriteId;
import com.tenminread.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "Favorites")
public class Favorite {
  @EmbeddedId
  private FavoriteId id;

  @MapsId("userId")
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "userid",
    foreignKey = @ForeignKey(name = "fk_fav_user"))
  private User user;

  @MapsId("bookid")
  @ManyToOne(optional = false)
  @JoinColumn(name = "bookid", referencedColumnName = "bookid",
    foreignKey = @ForeignKey(name = "fk_fav_book"))
  private Book book;
}
