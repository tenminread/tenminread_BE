package com.tenminread.bookdb.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "summary_text")
public class SummaryText {

  @EmbeddedId
  private SummaryTextId id;

  @Column(name = "summary_text", nullable = false, columnDefinition = "TEXT")
  private String summaryText;

  @Column(name = "version", nullable = false)
  private String version;

  public Integer getBookid() { return id != null ? id.getBookid() : null; }
  public Integer getSeq()    { return id != null ? id.getSeq()    : null; }
}
