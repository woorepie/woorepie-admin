package com.piehouse.wooreadmin.subscription.repository;

import com.piehouse.wooreadmin.estate.entity.EstateStatus;
import com.piehouse.wooreadmin.subscription.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByEstate_EstateId(Long estateId);

    @Query("SELECT s.estate.estateId, SUM(s.subTokenAmount) " +
            "FROM Subscription s " +
            "WHERE s.estate.estateStatus = :state " +
            "GROUP BY s.estate.estateId")
    List<Object[]> findTotalSubTokenAmountByEstate(@Param("state") EstateStatus state);

}
