package com.tenminread.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "UserScoreRadar")
public class UserScoreRadar {
  @Id
  @Column(name = "userid", nullable = false)
  private Integer userid;

  @OneToOne
  @JoinColumn(name = "userid", referencedColumnName = "userid",
    insertable = false, updatable = false)
  private User user;

  @Column(name = "literature")        private Integer literature;
  @Column(name = "history")           private Integer history;
  @Column(name = "philosophy")        private Integer philosophy;
  @Column(name = "socialScience")     private Integer socialScience;
  @Column(name = "naturalScience")    private Integer naturalScience;
  @Column(name = "technologyScience") private Integer technologyScience;
}
