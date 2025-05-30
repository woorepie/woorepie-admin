package com.piehouse.wooreadmin.subscription.service;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.EstateStatus;
import com.piehouse.wooreadmin.estate.repository.EstateRepository;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import com.piehouse.wooreadmin.subscription.dto.SubEstateRequest;
import com.piehouse.wooreadmin.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Transactional(readOnly = true)
    public List<SubEstateRequest> getEstateList() {

        List<Estate> estates = estateRepository.findEstateWithAgentByEstateStatus(EstateStatus.RUNNING);

        // 상태가 READY인 매물에 대해서만 총합 조회
        List<Object[]> tokenSums = subscriptionRepository.findTotalSubTokenAmountByEstate(EstateStatus.RUNNING);

        Map<Long, Integer> recruitTokenMap = tokenSums.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> ((Long) row[1]).intValue()
                ));

        return estates.stream()
                .map(estate -> SubEstateRequest.builder()
                        .estateId(estate.getEstateId())
                        .estateName(estate.getEstateName())
                        .agent(estate.getAgent())
                        .estateState(estate.getEstateState())
                        .estateCity(estate.getEstateCity())
                        .estateAddress(estate.getEstateAddress())
                        .tokenAmount(estate.getTokenAmount())
                        .recruitTokenAmount(recruitTokenMap.getOrDefault(estate.getEstateId(), 0))
                        .subStartDate(estate.getSubStartDate())
                        .subEndDate(estate.getSubEndDate())
                        .estateRegistrationDate(estate.getEstateRegistrationDate())
                        .estateStatus(estate.getEstateStatus())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public Boolean approveSubscription(Long estateId) {
        try{
            Estate estate = estateRepository.findById(estateId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + estateId));

            estate.updateSubState(EstateStatus.SUCCESS);
            estateRepository.save(estate);

            kafkaProducerService.sendSubscriptionCompleteEvent(estateId);

            return true;
        }catch (Exception e){
            log.error("매물 승인 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean rejectSubscription(Long estateId) {
        try{
            Estate estate = estateRepository.findById(estateId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + estateId));

            estate.updateSubState(EstateStatus.FAILURE);
            estateRepository.save(estate);

            kafkaProducerService.sendSubscriptionFailEvent(estateId);

            return true;
        }catch (Exception e){
            log.error("매물 거절 중 오류 발생: ", e);
            return false;
        }
    }

}
