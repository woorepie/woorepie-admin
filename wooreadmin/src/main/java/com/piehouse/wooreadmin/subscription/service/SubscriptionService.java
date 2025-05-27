package com.piehouse.wooreadmin.subscription.service;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.subscription.entity.Subscription;

import java.util.List;
import java.util.Optional;

public interface SubscriptionService {
    public Optional<List<Estate>> getEstateList();

    public Boolean approveSubscription(Long id);

    public Boolean rejectSubscription(Long id);
}
