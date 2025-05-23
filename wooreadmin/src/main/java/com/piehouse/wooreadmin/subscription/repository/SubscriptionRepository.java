package com.piehouse.wooreadmin.subscription.repository;

import com.piehouse.wooreadmin.dashboard.entity.SubState;
import com.piehouse.wooreadmin.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Query("SELECT s FROM Subscription s JOIN FETCH s.estate e WHERE e.subState = :state")
    Optional<List<Subscription>> findWithSubscriptionByState(@Param("state") SubState state);
}
