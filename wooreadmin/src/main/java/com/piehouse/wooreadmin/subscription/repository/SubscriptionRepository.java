package com.piehouse.wooreadmin.subscription.repository;

import com.piehouse.wooreadmin.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByEstate_EstateId(Long estateId);
}
