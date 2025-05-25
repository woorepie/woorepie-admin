package com.piehouse.wooreadmin.global.kafka.service;

import com.piehouse.wooreadmin.global.kafka.dto.DividendCompleteMessage;
import com.piehouse.wooreadmin.global.kafka.dto.SaleCompleteEvent;
import com.piehouse.wooreadmin.global.kafka.dto.SubscriptionCompleteMessage;

public interface KafkaProducerService {
    void sendSaleCompleteEvent(SaleCompleteEvent event);
    void sendDividendCompleteEvent(DividendCompleteMessage event);
    void sendSubscriptionCompleteEvent(Long estateId);
}
