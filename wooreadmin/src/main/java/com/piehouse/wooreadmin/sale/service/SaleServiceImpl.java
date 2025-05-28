package com.piehouse.wooreadmin.sale.service;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.SubState;
import com.piehouse.wooreadmin.estate.repository.EstateRepository;
import com.piehouse.wooreadmin.global.kafka.dto.DividendCompleteMessage;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SaleServiceImpl implements SaleService {

    private final EstateRepository estateRepository;
    private final KafkaProducerService kafkaProducerService;

    // 거래가능 매물 리스트 조회
    @Override
    @Transactional(readOnly = true)
    public List<Estate> getSuccessEstateList() {
        List<Estate> estates = estateRepository.findEstateWithAgentBySubState(SubState.SUCCESS);
        return estates;
    }

    @Override
    public boolean approveDividend(Long estateId, Integer dividend) {
        try{
            DividendCompleteMessage message = DividendCompleteMessage.builder()
                    .estate_id(estateId)
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
