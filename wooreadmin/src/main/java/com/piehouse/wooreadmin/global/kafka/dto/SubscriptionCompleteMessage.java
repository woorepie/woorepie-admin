package com.piehouse.wooreadmin.global.kafka.dto;


import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionCompleteMessage {
    private Long estateId;
    private List<CustomerSubscription> customer;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerSubscription {
        private Long customerId;
        private Integer tokenPrice;
        private Integer tradeTokenAmount;
    }
}

