package com.piehouse.wooreadmin.subscription.service;

import com.piehouse.wooreadmin.subscription.dto.SubEstateRequest;

import java.util.List;

public interface SubscriptionService {

    List<SubEstateRequest> getEstateList();

    Boolean approveSubscription(Long estateId);

    Boolean rejectSubscription(Long estateId);

}
