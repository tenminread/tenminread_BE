package com.tenminread.bookdb.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SummaryTextId implements Serializable {
  private Integer bookid;
  private Integer seq;
}
