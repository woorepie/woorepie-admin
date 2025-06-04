package com.piehouse.wooreadmin.estate.service;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.EstateStatus;
import com.piehouse.wooreadmin.estate.repository.EstateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EstateServiceImpl implements EstateService {

    private final EstateRepository estateRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Estate> getAllEstate() {

        List<Estate> estateList =  estateRepository.findEstateWithAgentByEstateStatus(EstateStatus.READY);

        return estateList;
    }

    @Override
    @Transactional
    public Boolean approveEstate(Long estateId) {
        try{
            Estate estate = estateRepository.findById(estateId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + estateId));

            estate.updateSubState(EstateStatus.RUNNING);
            estate.updateSubDate();

            estateRepository.save(estate);

            return true;
        }catch (Exception e){
            log.error("매물 승인 중 오류 발생: ", e);
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean rejectEstate(Long estateId) {
        try{
            Estate estate = estateRepository.findById(estateId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 매물을 찾을 수 없습니다. id=" + estateId));

            estate.updateSubState(EstateStatus.FAILURE);

            estateRepository.save(estate);

            return true;
        }catch (Exception e){
            log.error("매물 거절 중 오류 발생: ", e);
            return false;
        }
    }

}
