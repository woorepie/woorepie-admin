package com.piehouse.wooreadmin.estate.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "estate")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@ToString(exclude = "agent")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estate {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @Column(nullable = false, length = 20)
    private String estateName;

    @Column(nullable = false, length = 10)
    private String estateState;

    @Column(nullable = false, length = 10)
    private String estateCity;

    @Column(nullable = false, length = 100)
    private String estateAddress;

    @Column(nullable = false, length = 50)
    private String estateLatitude;

    @Column(nullable = false, length = 50)
    private String estateLongitude;

    @Column(nullable = false)
    private Integer tokenAmount;

    @Column(columnDefinition = "TEXT")
    private String estateDescription;

    private LocalDateTime subStartDate;

    private LocalDateTime subEndDate;

    @Column(length = 1000)
    private String estateImageUrl;

    @Column(unique = true, length = 200)
    private String tokenAddress;

    @Column(nullable = false, length = 1000)
    private String subGuideUrl;

    @Column(nullable = false, length = 1000)
    private String securitiesReportUrl;

    @Column(nullable = false, length = 1000)
    private String investmentExplanationUrl;

    @Column(nullable = false, length = 1000)
    private String propertyMngContractUrl;

    @Column(nullable = false, length = 1000)
    private String appraisalReportUrl;

    @Column(updatable = false)
    private LocalDateTime estateRegistrationDate;

    @Column(nullable = false, length = 20)
    private String estateUseZone;

    @Column(nullable = false)
    private BigDecimal totalEstateArea;

    @Column(nullable = false)
    private BigDecimal tradedEstateArea;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubState subState;

    // 매물 정보 수정
    public Estate updateDescription(String newDescription) {
        this.estateDescription = newDescription;
        return this;
    }


    // 매물 상태 수정
    public Estate updateSubState(SubState newSubState) {
        this.subState = newSubState;
        return this;
    }

    // 청약 기간 설정
    public Estate updateSubDate() {
        this.subStartDate = LocalDateTime.now();
        this.subEndDate = this.subStartDate.plusWeeks(2);
        return this;
    }

}