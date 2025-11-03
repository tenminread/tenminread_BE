package com.tenminread.bookdb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SummaryTextId implements Serializable {

  @Column(name = "bookid", nullable = false)
  private Integer bookid;

  @Column(name = "seq", nullable = false)
  private Integer seq;
}
