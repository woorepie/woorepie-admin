package com.piehouse.wooreadmin.global.kafka.service;

import com.piehouse.wooreadmin.global.kafka.dto.DividendCompleteMessage;
import com.piehouse.wooreadmin.global.kafka.dto.CompleteEvent;

public interface KafkaProducerService {

    void sendSaleCompleteEvent(CompleteEvent event);

    void sendDividendCompleteEvent(DividendCompleteMessage event);

    void sendSubscriptionCompleteEvent(Long estateId);

    void sendSubscriptionFailEvent(Long estateId);

}
