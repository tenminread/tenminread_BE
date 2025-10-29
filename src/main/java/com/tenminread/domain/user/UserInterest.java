package com.tenminread.domain.user;

import com.tenminread.domain.category.Category;
import com.tenminread.domain.common.id.UserInterestId;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "UserInterest")
public class UserInterest {
  @EmbeddedId
  private UserInterestId id;

  @MapsId("userId")
  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "userid",
    foreignKey = @ForeignKey(name = "fk_ui_user"))
  private User user;

  @MapsId("categoryid")
  @ManyToOne(optional = false)
  @JoinColumn(name = "categoryid", referencedColumnName = "categoryid",
    foreignKey = @ForeignKey(name = "fk_ui_category"))
  private Category category;
}
