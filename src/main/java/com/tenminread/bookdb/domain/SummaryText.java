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

  @Lob
  @Column(name = "summary_text", nullable = false, columnDefinition = "TEXT")
  private String summaryText;

  @Column(name = "version", nullable = false)
  private String version;           // DB DEFAULT 'v1'

  // summary.getId().getBookid() 대신 summary.getBookid()로 간단하게 접근하도록 붙임
  public Integer getBookid() { return id != null ? id.getBookid() : null; }
  public Integer getSeq()    { return id != null ? id.getSeq()    : null; }
}
