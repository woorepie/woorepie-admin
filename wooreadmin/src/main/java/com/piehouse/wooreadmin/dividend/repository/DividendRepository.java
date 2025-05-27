package com.piehouse.wooreadmin.dividend.repository;

import com.piehouse.wooreadmin.dividend.entity.Dividend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DividendRepository extends JpaRepository<Dividend, Long> {

    @Query("SELECT d FROM Dividend d JOIN FETCH d.estate e")
    List<Dividend> findALlDividends();
}
