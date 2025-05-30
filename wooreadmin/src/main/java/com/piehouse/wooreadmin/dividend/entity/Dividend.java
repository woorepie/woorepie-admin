package com.piehouse.wooreadmin.dividend.entity;
import com.piehouse.wooreadmin.estate.entity.Estate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "dividend")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(exclude = "estate")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Dividend {

  @EqualsAndHashCode.Include
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long dividendId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estate_id", nullable = false)
  private Estate estate;

  @Column(nullable = false)
  private Integer dividend;

  @Column(nullable = false)
  private BigDecimal dividendYield;

  @CreationTimestamp
  private LocalDateTime dividendDate;

}