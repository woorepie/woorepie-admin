package com.piehouse.wooreadmin.global.aws;

import com.piehouse.wooreadmin.global.aws.dto.S3UrlResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface S3Service {
    String getPublicS3Url(String key);

    public String uploadEstateDisclosureFile(Long estateId, MultipartFile file);

}
