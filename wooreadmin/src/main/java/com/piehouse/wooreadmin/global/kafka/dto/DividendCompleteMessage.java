package com.piehouse.wooreadmin.global.kafka.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DividendCompleteMessage {

    private Long estate_id;

    private Integer dividend;

}
