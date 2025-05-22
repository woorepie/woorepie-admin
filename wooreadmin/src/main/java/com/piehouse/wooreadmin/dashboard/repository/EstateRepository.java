package com.piehouse.wooreadmin.dashboard.repository;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstateRepository extends JpaRepository<Estate, Long> {
}
