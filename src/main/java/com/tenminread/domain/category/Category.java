package com.tenminread.domain.category;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Category",
  uniqueConstraints = @UniqueConstraint(name = "uk_category_name", columnNames = "categoryName")
)
public class Category {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "categoryid", nullable = false)
  private Integer categoryid;

  @Column(name = "categoryName", nullable = false, length = 100)
  private String categoryName;
}
