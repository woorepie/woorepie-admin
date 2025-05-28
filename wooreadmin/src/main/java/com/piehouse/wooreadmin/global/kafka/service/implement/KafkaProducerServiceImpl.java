package com.piehouse.wooreadmin.global.kafka.service.implement;

import com.piehouse.wooreadmin.global.kafka.dto.DividendCompleteMessage;
import com.piehouse.wooreadmin.global.kafka.dto.CompleteEvent;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import com.piehouse.wooreadmin.global.kafka.util.KafkaRetryUtil;
import com.piehouse.wooreadmin.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final SubscriptionRepository subscriptionRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaRetryUtil kafkaRetryUtil;
    private static final String DIVIDEND_RESPONSE_TOPIC = "dividend.accept";
    private static final String SALE_RESPONSE_TOPIC = "exit.accept";
    private static final String SUBSCRIPTION_SUCCESS_TOPIC = "subscription.success";
    private static final String SUBSCRIPTION_FAILURE_TOPIC = "subscription.failure";

    @Override
    public void sendSaleCompleteEvent(Long estateId) {

        CompleteEvent event = CompleteEvent.builder()
                .estateId(estateId)
                .build();

        send(SALE_RESPONSE_TOPIC, event);

    }

    @Override
    public void sendDividendCompleteEvent(DividendCompleteMessage event) {
        send(DIVIDEND_RESPONSE_TOPIC, event);
    }

    @Override
    public void sendSubscriptionCompleteEvent(Long estateId) {

        CompleteEvent event = CompleteEvent.builder()
                .estateId(estateId)
                .build();

        send(SUBSCRIPTION_SUCCESS_TOPIC, event);

    }

    @Override
    public void sendSubscriptionFailEvent(Long estateId) {

        CompleteEvent event = CompleteEvent.builder()
                .estateId(estateId)
                .build();

        send(SUBSCRIPTION_FAILURE_TOPIC, event);

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
