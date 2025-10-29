package com.tenminread.domain.common.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Embeddable
public class FavoriteId implements Serializable {
  private Integer userId;
  private Integer bookid;
}
