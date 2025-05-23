package com.piehouse.wooreadmin.global.kafka.service.implement;

import com.piehouse.wooreadmin.global.kafka.service.KafkaConsumerService;
import com.piehouse.wooreadmin.global.kafka.util.KafkaRetryUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl implements KafkaConsumerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaRetryUtil kafkaRetryUtil;





}
