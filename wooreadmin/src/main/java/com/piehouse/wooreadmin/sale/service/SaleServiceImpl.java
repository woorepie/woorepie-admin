package com.piehouse.wooreadmin.sale.service;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.SubState;
import com.piehouse.wooreadmin.estate.repository.EstateRepository;
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
    @Transactional
    public boolean approveSaleEstate(Long estateId) {
        try{
            Estate estate = estateRepository.findById(estateId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + estateId));

            estate.updateSubState(SubState.EXIT);
            estateRepository.save(estate);

            kafkaProducerService.sendSaleCompleteEvent(estateId);

            return true;
        }catch (Exception e){
            log.error("매물 매각 승인 중 오류 발생: ", e);
            return false;
        }
    }

}
