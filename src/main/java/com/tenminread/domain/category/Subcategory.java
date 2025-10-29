package com.tenminread.domain.category;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(
  name = "Subcategory",
  indexes = @Index(name = "idx_subcat_category", columnList = "categoryid")
)
public class Subcategory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "subcategoryid", nullable = false)
  private Integer subcategoryid;

  @Column(name = "subcategoryName", nullable = false, length = 100)
  private String subcategoryName;

  @ManyToOne(optional = false)
  @JoinColumn(name = "categoryid", referencedColumnName = "categoryid",
    foreignKey = @ForeignKey(name = "fk_subcat_category"))
  private Category category;
}
