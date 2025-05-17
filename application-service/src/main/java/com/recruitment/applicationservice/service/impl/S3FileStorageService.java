package com.recruitment.applicationservice.service.impl;

import com.recruitment.applicationservice.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class S3FileStorageService implements FileStorageService {

    @Value("${application.storage.s3.bucket:application-files}")
    private String bucketName;

    @Value("${application.storage.s3.region:us-east-1}")
    private String region;

    @Override
    public String storeFile(MultipartFile file) {
        try {
            String fileName = generateFileName(file);

            // Mock implementation - in production, replace with actual S3 upload code
            return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        // Mock implementation - in production, implement actual deletion logic
    }

    private String generateFileName(MultipartFile file) {
        String extension = getFileExtension(file.getOriginalFilename());
        return UUID.randomUUID().toString() + extension;
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastIndex = fileName.lastIndexOf(".");
        return (lastIndex == -1) ? "" : fileName.substring(lastIndex);
    }
}