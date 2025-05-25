package com.piehouse.wooreadmin.global.aws;

import com.piehouse.wooreadmin.global.aws.dto.S3UrlResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class S3ServiceImpl implements S3Service {
    private final S3Client s3Client;
    private final String bucketName;
    private final String region;

    private final Duration validFor = Duration.ofMinutes(5);

    public S3ServiceImpl(S3Client s3Client, @Value("${aws.s3.bucket}") String bucketName, @Value("${aws.region}") String region) {
        this.s3Client = s3Client;
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
    public String uploadEstateDisclosureFile(Long estateId, MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = Optional.ofNullable(originalFilename)
                    .filter(f -> f.contains("."))
                    .map(f -> f.substring(originalFilename.lastIndexOf(".")))
                    .orElse("");

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String key = String.format("estate/%d/%s-%s%s", estateId, "disclosure", timestamp, extension);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);
        } catch (IOException e) {
            throw new RuntimeException("S3 파일 업로드 실패", e);
        }
    }


}