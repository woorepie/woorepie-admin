package com.piehouse.wooreadmin.dividend.repository;

import com.piehouse.wooreadmin.dividend.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DividendRepository extends JpaRepository<Dividend, Long> {
}
