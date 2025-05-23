package com.piehouse.wooreadmin.notice.service;

import com.piehouse.wooreadmin.dashboard.entity.Estate;
import com.piehouse.wooreadmin.notice.entity.Notice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface NoticeService {

    public Optional<List<Estate>> getAllEstates();

    public Boolean addNotice(String noticeTitle, String noticeContent, Long estateId, MultipartFile file);

    public String uploadFile(MultipartFile file);
}
