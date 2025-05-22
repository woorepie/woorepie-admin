package com.piehouse.wooreadmin.notice.repository;

import com.piehouse.wooreadmin.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
