package com.piehouse.wooreadmin.subscription.service;

import com.piehouse.wooreadmin.dashboard.entity.Estate;

import java.util.List;

public interface SubscriptionService {

    List<Estate> getEstateList();

    Boolean approveSubscription(Long id);

    Boolean rejectSubscription(Long id);

}
