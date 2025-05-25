package com.piehouse.wooreadmin.global.aws;

import com.piehouse.wooreadmin.global.aws.dto.S3UrlResponse;

import java.util.List;

public interface S3Service {
    String getPublicS3Url(String key);

    List<S3UrlResponse> generateEstatePresignedUrl(String domain, String estateAddress);

}
