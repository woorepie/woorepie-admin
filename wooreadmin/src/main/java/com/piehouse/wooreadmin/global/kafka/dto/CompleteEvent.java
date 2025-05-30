package com.piehouse.wooreadmin.global.kafka.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CompleteEvent {

    private Long estateId;

}