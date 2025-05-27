package com.piehouse.wooreadmin.notice.service;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.dashboard.repository.DashboardRepository;
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
    private final DashboardRepository dashboardRepository;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Estate> getAllEstates() {

        List<Estate> estates = dashboardRepository.findAll();

        return estates;
    }

    @Override
    @Transactional
    public Boolean addNotice(String noticeTitle, String noticeContent, Long estateId, MultipartFile file) {
        String noticeFileUrl = null;

        if (file != null && !file.isEmpty()) {
            noticeFileUrl = s3Service.uploadEstateDisclosureFile(estateId, file);
        }

        Notice notice = Notice.builder()
                .estate(estateId)  // estate 필드명에 맞게 수정
                .noticeTitle(noticeTitle)
                .noticeContent(noticeContent)
                .noticeFileUrl(noticeFileUrl)
                .build();

        noticeRepository.save(notice);


        return true;
    }

}
