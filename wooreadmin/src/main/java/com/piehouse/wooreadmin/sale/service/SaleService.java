package com.piehouse.wooreadmin.sale.service;

import com.piehouse.wooreadmin.estate.entity.Estate;

import java.util.List;

public interface SaleService {

    List<Estate> getSuccessEstateList();

    boolean approveDividend(Long estateId, Integer dividend);

}
