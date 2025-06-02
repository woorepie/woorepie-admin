package com.piehouse.wooreadmin.dividend.service;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.EstateStatus;
import com.piehouse.wooreadmin.estate.repository.EstateRepository;
import com.piehouse.woorepie.global.kafka.dto.DividendAcceptEvent;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DividendServiceImpl implements DividendService {

    private final EstateRepository estateRepository;
    private final KafkaProducerService kafkaProducerService;

    // 거래가능 매물 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<Estate> getSuccessEstateList() {
        List<Estate> estates = estateRepository.findEstateWithAgentByEstateStatus(EstateStatus.SUCCESS);
        return estates;
    }

    @Override
    public boolean approveDividend(Long estateId, Integer dividend) {
        try{
            DividendAcceptEvent message = DividendAcceptEvent.builder()
                    .estateId(estateId)
                    .dividend(dividend)
                    .build();
            kafkaProducerService.sendDividendCompleteEvent(message);
            return true;
        }catch (Exception e){
            log.error("배당금 승인 중 오류 발생: ", e);
            return false;
        }
    }

}
