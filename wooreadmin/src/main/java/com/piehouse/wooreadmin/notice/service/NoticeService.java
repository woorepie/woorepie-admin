package com.piehouse.wooreadmin.notice.service;

import com.piehouse.wooreadmin.estate.entity.Estate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoticeService {

    List<Estate> getAllEstates();

    Boolean addNotice(String noticeTitle, String noticeContent, Long estateId, MultipartFile file);
}
