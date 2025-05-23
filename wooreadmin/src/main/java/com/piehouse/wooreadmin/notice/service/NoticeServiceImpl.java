package com.piehouse.wooreadmin.notice.service;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.dashboard.repository.DashboardRepository;
import com.piehouse.wooreadmin.notice.entity.Notice;
import com.piehouse.wooreadmin.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final DashboardRepository dashboardRepository;
    private final NoticeRepository noticeRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<List<Estate>> getAllEstates() {
        Optional<List<Estate>> estates = Optional.of(dashboardRepository.findAll());

        return estates;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        return "";
    }

    @Override
    @Transactional
    public Boolean addNotice(String noticeTitle, String noticeContent, Long estateId, MultipartFile file) {
        String noticeFileUrl = null;

        if (file != null && !file.isEmpty()) {
            noticeFileUrl = uploadFile(file);
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
