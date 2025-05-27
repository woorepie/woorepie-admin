package com.piehouse.wooreadmin.dashboard.repository;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.dashboard.entity.SubState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DashboardRepository extends JpaRepository<Estate, Long> {

    @Query("SELECT e FROM Estate e JOIN FETCH e.agent WHERE e.subState = :state")
    List<Estate> findWithAgentById(@Param("state")SubState state);

    List<Estate> findEstateBySubState(SubState state);
}
