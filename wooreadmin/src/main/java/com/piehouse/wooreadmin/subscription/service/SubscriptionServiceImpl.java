package com.piehouse.wooreadmin.subscription.service;

import com.piehouse.wooreadmin.estate.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.SubState;
import com.piehouse.wooreadmin.estate.repository.EstateRepository;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import com.piehouse.wooreadmin.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final EstateRepository estateRepository;
    private final KafkaProducerService kafkaProducerService;

//    @Override
//    public Optional<List<Subscription>> getSubscriptionList() {
//        return subscriptionRepository.findWithSubscriptionByState(SubState.PENDING);
//    }

    @Override
    public List<Estate> getEstateList() {
        return estateRepository.findEstateBySubState(SubState.READY);
    }


    @Override
    @Transactional
    public Boolean approveSubscription(EstateApproveRequest dto) {
        try{
            Estate estate = estateRepository.findById(dto.getEstateId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + dto.getEstateId()));

            estate.updateSubState(SubState.SUCCESS);
            estateRepository.save(estate);

            kafkaProducerService.sendSubscriptionCompleteEvent(dto.getEstateId());

            return true;
        }catch (Exception e){
            log.error("매물 승인 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean rejectSubscription(EstateApproveRequest dto) {
        try{
            Estate estate = estateRepository.findById(dto.getEstateId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + dto.getEstateId()));

            estate.updateSubState(SubState.SUCCESS);
            estateRepository.save(estate);

            return true;
        }catch (Exception e){
            log.error("매물 거절 중 오류 발생: ", e);
            return false;
        }
    }

}
