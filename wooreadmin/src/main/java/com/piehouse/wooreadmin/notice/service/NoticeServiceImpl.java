package com.piehouse.wooreadmin.notice.service;

import com.piehouse.wooreadmin.estate.entity.Estate;
import com.piehouse.wooreadmin.estate.entity.EstateStatus;
import com.piehouse.wooreadmin.estate.repository.EstateRepository;
import com.piehouse.wooreadmin.global.aws.S3Service;
import com.piehouse.wooreadmin.notice.entity.Notice;
import com.piehouse.wooreadmin.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final S3Service s3Service;
    private final EstateRepository estateRepository;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Estate> getAllEstates() {

        List<Estate> estates = estateRepository.findEstateWithAgentByEstateStatusIn(
                List.of(EstateStatus.SUCCESS, EstateStatus.READY)
        );

        return estates;
    }

    @Override
    @Transactional
    public Boolean addNotice(String noticeTitle, String noticeContent, Long estateId, MultipartFile file) {
        String noticeFileUrl = null;

        if (file != null && !file.isEmpty()) {
            noticeFileUrl = s3Service.uploadEstateDisclosureFile(estateId, file);
        }

        Estate estate = estateRepository.findById(estateId)
                .orElseThrow(() -> new RuntimeException("Estate not found"));

        Notice notice = Notice.builder()
                .estate(estate)
                .noticeTitle(noticeTitle)
                .noticeContent(noticeContent)
                .noticeFileUrl(noticeFileUrl)
                .build();

        noticeRepository.save(notice);

        return true;
    }

}
