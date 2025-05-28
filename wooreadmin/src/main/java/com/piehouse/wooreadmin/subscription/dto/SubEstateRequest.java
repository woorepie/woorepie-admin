package com.piehouse.wooreadmin.subscription.dto;

import com.piehouse.wooreadmin.estate.entity.Agent;
import com.piehouse.wooreadmin.estate.entity.SubState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
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

    private Integer tokenAmount;

    private Integer recruitTokenAmount;

    private LocalDateTime subStartDate;

    private LocalDateTime subEndDate;

    private LocalDateTime estateRegistrationDate;

    private SubState subState;

}
