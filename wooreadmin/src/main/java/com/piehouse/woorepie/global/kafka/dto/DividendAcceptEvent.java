package com.piehouse.woorepie.global.kafka.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DividendAcceptEvent {

    private Long estateId;

    private Integer dividend;

}
