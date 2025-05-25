package com.piehouse.wooreadmin.dashboard.service;

import com.piehouse.wooreadmin.dashboard.entity.Estate;

import java.util.List;
import java.util.Optional;

public interface DashboardService {
    public Optional<List<Estate>> getAllEstate();

    public Boolean approveEstate(Long id);

    public Boolean rejectEstate(Long id);
}
