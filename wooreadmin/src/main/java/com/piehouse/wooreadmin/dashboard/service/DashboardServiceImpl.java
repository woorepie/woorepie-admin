package com.piehouse.wooreadmin.dashboard.service;

import com.piehouse.wooreadmin.dashboard.dto.EstateApproveRequest;
import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.dashboard.entity.SubState;
import com.piehouse.wooreadmin.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardRepository dashboardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Estate> getAllEstate() {
        List<Estate> estateList =  dashboardRepository.findWithAgentById(SubState.READY);

        return estateList;
    }

    @Override
    @Transactional
    public Boolean approveEstate(EstateApproveRequest dto) {
        try{
            Estate estate = new Estate();
            estate.setEstateId(dto.getEstateId());
            estate.setSubState(SubState.RUNNING);
            dashboardRepository.save(estate);
            return true;
        }catch (Exception e){
            log.error("매물 승인 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean rejectEstate(EstateApproveRequest dto) {
        try{
            Estate estate = new Estate();
            estate.setEstateId(dto.getEstateId());
            estate.setSubState(SubState.FAILURE);
            dashboardRepository.save(estate);
            return true;
        }catch (Exception e){
            log.error("매물 거절 중 오류 발생: ", e);
            return false;
        }
    }
}
