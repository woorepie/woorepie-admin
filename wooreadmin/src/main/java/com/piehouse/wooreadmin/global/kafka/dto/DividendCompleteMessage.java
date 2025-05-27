package com.piehouse.wooreadmin.global.kafka.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class DividendCompleteMessage {

    private Long estate_id;
    private Float dividend_yield;
}
