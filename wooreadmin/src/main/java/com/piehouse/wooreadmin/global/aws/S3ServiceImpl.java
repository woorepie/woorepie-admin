package com.piehouse.wooreadmin.global.aws;

import com.piehouse.wooreadmin.global.aws.dto.S3UrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class S3ServiceImpl implements S3Service {
    private final S3Presigner presigner;
    private final String bucketName;
    private final String region;

    private final Duration validFor = Duration.ofMinutes(5);

    public S3ServiceImpl(S3Presigner presigner, @Value("${aws.s3.bucket}") String bucketName, @Value("${aws.region}") String region) {
        this.presigner = presigner;
        this.bucketName = bucketName;
        this.region = region;
    }

    @Override
    public String getPublicS3Url(String key) {
        System.out.println(String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,
                key
        ));
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName,
                region,
                key
        );
    }

    @Override
    public List<S3UrlResponse> generateEstatePresignedUrl(String domain, String estateId) {
        String[] files = {"estate-image", "sub-guide", "securities-report", "investment-explanation",
                "property-mng-contract", "appraisal-report"};
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        return Arrays.stream(files)
                .map(fileType -> {
                    // 상위에 estate 디렉토리를 추가
                    String objectKey = String.format("%s/estate/%s/%s-%s", domain, estateId, fileType, timestamp);

                    PutObjectRequest putReq = PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(objectKey)
                            .build();

                    PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(p -> p
                            .signatureDuration(validFor)
                            .putObjectRequest(putReq)
                    );

                    return S3UrlResponse.builder()
                            .url(presignedRequest.url().toString())
                            .key(objectKey)
                            .expiresIn(validFor.getSeconds())
                            .build();
                })
                .toList();
    }


}