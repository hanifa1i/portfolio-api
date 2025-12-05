package com.hanif.portfolioapi.service;

import com.hanif.portfolioapi.config.aws.AwsProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final S3Client s3Client;
    private final AwsProperties awsProperties;

    public String uploadFile(String type, Long id, MultipartFile file) throws IOException {

        String fileName = createFilename(file.getOriginalFilename());

        String key = type + "/" + id + "/" + fileName;

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(awsProperties.getBucket())
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        return generateUrl(key);
    }

    public void deleteFile(String key) {
        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(awsProperties.getBucket())
                .key(key)
                .build();

        s3Client.deleteObject(request);
    }

    private String createFilename(String originalFilename) {

        if (originalFilename == null || !originalFilename.contains(".")) {
            return "";
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        return UUID.randomUUID() + extension;
    }

    private String generateUrl(String key) {
        return "https://"
                + awsProperties.getBucket()
                + ".s3."
                + awsProperties.getRegion()
                + ".amazonaws.com/"
                + key;
    }

}
