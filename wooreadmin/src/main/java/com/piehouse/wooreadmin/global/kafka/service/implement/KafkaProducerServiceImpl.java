package com.piehouse.wooreadmin.global.kafka.service.implement;

import com.piehouse.wooreadmin.global.kafka.dto.DividendCompleteMessage;
import com.piehouse.wooreadmin.global.kafka.dto.SaleCompleteEvent;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import com.piehouse.wooreadmin.global.kafka.util.KafkaRetryUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@NoArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private KafkaTemplate<String, Object> kafkaTemplate;
    private KafkaRetryUtil kafkaRetryUtil;
    private static final String DIVIDEND_RESPONSE_TOPIC = "dividend.accept";
    private static final String SALE_RESPONSE_TOPIC = "sale.accept";



    @Override
    public void sendSaleCompleteEvent(SaleCompleteEvent event) {
        send(SALE_RESPONSE_TOPIC, event);
    }

    @Override
    public void sendDividendCompleteEvent(DividendCompleteMessage event) {
        send(DIVIDEND_RESPONSE_TOPIC, event);
    }

    private <T> void send(String topic, T event) {
        kafkaTemplate.send(topic, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Kafka 전송 성공 [{}]: {}", topic, event.toString());
                    } else {
                        log.warn("Kafka 전송 실패 [{}], retrying...", topic, ex);
                        kafkaRetryUtil.sendWithRetry(topic, event, 3);
                    }
                });
    }

    private <T> void sendWithKey(String topic, String key, T event) {
        kafkaTemplate.send(topic, key, event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Kafka 전송 성공 [{}][key:{}]: {}", topic, key, event);
                    } else {
                        log.warn("Kafka 전송 실패 [{}][key:{}], retrying...", topic, key, ex);
                        kafkaRetryUtil.sendWithRetry(topic, key, event, 3);
                    }
                });
    }

}
