package com.tenminread.domain.user;

import com.tenminread.domain.common.YesNo;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "User")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userid", nullable = false)
  private Integer userid;

  @Enumerated(EnumType.STRING)
  @Column(name = "kakaoUser", nullable = false, columnDefinition = "ENUM('Y','N')")
  private YesNo kakaoUser;

  @Column(name = "nickName", nullable = false, length = 10)
  private String nickName;
}
