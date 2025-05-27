package com.piehouse.wooreadmin.estate.service;

import com.piehouse.wooreadmin.estate.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.estate.entity.Estate;

import java.util.List;

public interface EstateService {

    List<Estate> getAllEstate();

    Boolean approveEstate(EstateApproveRequest dto);

    Boolean rejectEstate(EstateApproveRequest dto);

}
