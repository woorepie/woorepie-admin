package com.piehouse.wooreadmin.subscription.entity;

import com.piehouse.wooreadmin.estate.entity.Estate;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(exclude = {"estate", "customer"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subscription {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estate_id", nullable = false)
    private Estate estate;

    @Column(name = "customer_id", insertable = false, updatable = false)
    private Long customer;

    @Column(nullable = false)
    private Long subTokenAmount;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime subDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "sub_status", nullable = false)
    private SubStatus subStatus = SubStatus.PENDING;

}