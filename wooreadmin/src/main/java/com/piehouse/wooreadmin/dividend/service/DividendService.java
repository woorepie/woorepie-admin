package com.piehouse.wooreadmin.dividend.service;

import com.piehouse.wooreadmin.estate.entity.Estate;

import java.util.List;

public interface DividendService {

    List<Estate> getSuccessEstateList();

    boolean approveDividend(Long estateId, Integer dividend);

}
