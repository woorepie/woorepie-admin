package com.piehouse.wooreadmin.subscription.dto;

import com.piehouse.wooreadmin.estate.entity.Agent;
import com.piehouse.wooreadmin.estate.entity.EstateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class SubEstateRequest {

    private Long estateId;

    private Agent agent;

    private String estateName;

    private String estateState;

    private String estateCity;

    private String estateAddress;

    private Long tokenAmount;

    private Long recruitTokenAmount;

    private Long estateSalePrice;

    private LocalDateTime subStartDate;

    private LocalDateTime subEndDate;

    private LocalDateTime estateRegistrationDate;

    private EstateStatus estateStatus;

}
