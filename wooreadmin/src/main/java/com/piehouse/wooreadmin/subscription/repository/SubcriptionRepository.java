package com.piehouse.wooreadmin.subscription.repository;

import com.piehouse.wooreadmin.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcriptionRepository extends JpaRepository<Subscription, Long> {
}
