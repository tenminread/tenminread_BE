package com.tenminread.domain.common.id;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
@Embeddable
public class QuizRecordId implements Serializable {
  private Integer userId;
  private Integer quizid;
}
