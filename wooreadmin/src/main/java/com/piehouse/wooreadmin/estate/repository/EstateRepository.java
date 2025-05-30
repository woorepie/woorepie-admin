package com.piehouse.wooreadmin.estate.repository;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.EstateStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstateRepository extends JpaRepository<Estate, Long> {

    @Query("SELECT e FROM Estate e JOIN FETCH e.agent WHERE e.estateStatus = :state")
    List<Estate> findEstateWithAgentByEstateStatus(@Param("state") EstateStatus state);

    @Query("SELECT e FROM Estate e JOIN FETCH e.agent WHERE e.estateStatus IN :states")
    List<Estate> findEstateWithAgentByEstateStatusIn(@Param("states") List<EstateStatus> states);

}
