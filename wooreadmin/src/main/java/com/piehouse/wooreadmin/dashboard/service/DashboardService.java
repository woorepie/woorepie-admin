package com.piehouse.wooreadmin.dashboard.service;

import com.piehouse.wooreadmin.dashboard.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.dashboard.entity.Estate;

import java.util.List;

public interface DashboardService {

    List<Estate> getAllEstate();

    Boolean approveEstate(EstateApproveRequest dto);

    Boolean rejectEstate(EstateApproveRequest dto);

}
