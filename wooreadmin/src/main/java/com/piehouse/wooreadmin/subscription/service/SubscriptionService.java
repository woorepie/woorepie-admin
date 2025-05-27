package com.piehouse.wooreadmin.subscription.service;

import com.piehouse.wooreadmin.estate.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.estate.entity.Estate;

import java.util.List;

public interface SubscriptionService {

    List<Estate> getEstateList();

    Boolean approveSubscription(EstateApproveRequest dto);

    Boolean rejectSubscription(EstateApproveRequest dto);

}
