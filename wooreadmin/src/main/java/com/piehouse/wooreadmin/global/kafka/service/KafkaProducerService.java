package com.piehouse.wooreadmin.global.kafka.service;

import com.piehouse.woorepie.global.kafka.dto.DividendAcceptEvent;

public interface KafkaProducerService {

    void sendSaleCompleteEvent(Long estateId);

    void sendDividendCompleteEvent(DividendAcceptEvent event);

    void sendSubscriptionCompleteEvent(Long estateId);

    void sendSubscriptionFailEvent(Long estateId);

}
