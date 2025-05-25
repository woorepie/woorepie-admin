package com.piehouse.wooreadmin.subscription.service;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.dashboard.entity.SubState;
import com.piehouse.wooreadmin.dashboard.repository.DashboardRepository;
import com.piehouse.wooreadmin.global.kafka.service.KafkaProducerService;
import com.piehouse.wooreadmin.subscription.entity.Subscription;
import com.piehouse.wooreadmin.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final DashboardRepository dashboardRepository;
    private final KafkaProducerService kafkaProducerService;

//    @Override
//    public Optional<List<Subscription>> getSubscriptionList() {
//        return subscriptionRepository.findWithSubscriptionByState(SubState.PENDING);
//    }

    @Override
    public Optional<List<Estate>> getEstateList() {
        return dashboardRepository.findEstateBySubState(SubState.PENDING);
    }


    @Override
    @Transactional
    public Boolean approveSubscription(Long id) {
        try{
            Estate estate = dashboardRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + id));
            estate.setSubState(SubState.SUCCESS);
            dashboardRepository.save(estate);

            kafkaProducerService.sendSubscriptionCompleteEvent(id);
            return true;
        }catch (Exception e){
            log.error("매물 승인 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean rejectSubscription(Long id) {
        try{
            Estate estate = new Estate();
            estate.setEstateId(id);
            estate.setSubState(SubState.FAILURE);
            dashboardRepository.save(estate);
            return true;
        }catch (Exception e){
            log.error("매물 거절 중 오류 발생: ", e);
            return false;
        }
    }


}
