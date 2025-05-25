package com.piehouse.wooreadmin.global.aws.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class S3UrlResponse {

    private String url;

    private String key;

    private long expiresIn;

}