package com.example.hcnstore_backend.storage.internal.service;

import com.cloudinary.Cloudinary;
import com.example.hcnstore_backend.core.enums.CommonCode;
import com.example.hcnstore_backend.core.exception.BaseException;
import com.example.hcnstore_backend.storage.dto.UploadSignatureResponse;
import com.example.hcnstore_backend.storage.enums.StorageResponseCode;
import com.example.hcnstore_backend.storage.exception.StorageException;
import com.example.hcnstore_backend.storage.internal.config.CloudinaryConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;
    private final CloudinaryConfig cloudinaryConfig;
    @Value("${cloudinary.project}")
    private String project;

    @SuppressWarnings("unchecked")
    public Map<String, Object> uploadImage(MultipartFile file, String folder, String publicId) {
        try {
            Map<String, Object> options = Map.of(
                    "folder", getFolder(folder),
                    "public_id", publicId,
                    "overwrite", true,
                    "invalidate", true,
                    "resource_type", "image"
            );
            return cloudinary.uploader()
                    .upload(file.getBytes(), options);
        } catch (IOException e) {
            log.error("Failed to upload image to Cloudinary:", e);
            throw new BaseException(CommonCode.INTERNAL_SERVER_ERROR) {
            };
        }
    }

    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader()
                    .destroy(publicId, null);
        } catch (IOException e) {
            log.error("Failed to delete image from Cloudinary:", e);
            throw new BaseException(CommonCode.INTERNAL_SERVER_ERROR) {
            };
        }
    }

    public String getImageUrl(String publicId, String version) {
        return cloudinary.url()
                .version(version)
                .generate(publicId);
    }

    public UploadSignatureResponse generateUploadSignature(String folder, String publicId) {
        long timestamp = System.currentTimeMillis() / 1000;
        String folderPath = getFolder(folder);
        Map<String, Object> params = Map.of(
                "folder", folderPath,
                "public_id", publicId,
                "timestamp", timestamp
        );
        String signature = cloudinary.apiSignRequest(params, cloudinaryConfig.getApiSecret());
        String uploadUrl = cloudinaryConfig.getUploadUrl();
        return UploadSignatureResponse.builder()
                .folder(folderPath)
                .signature(signature)
                .publicId(publicId)
                .timestamp(timestamp)
                .apiKey(cloudinary.config.apiKey)
                .uploadUrl(uploadUrl)
                .build();
    }

    public void verifyFile(String publicId, String version) {
        try {
            Map<?, ?> resourceInfo = cloudinary.api()
                    .resource(publicId, Map.of("version", version));
            if (resourceInfo == null || resourceInfo.isEmpty()) {
                throw new StorageException(StorageResponseCode.FILE_NOT_FOUND);
            }
        } catch (Exception e) {
            log.info("Failed to verify file: {}", e.getMessage());
            throw new StorageException(StorageResponseCode.INVALID_FILE);
        }
    }

    private String getFolder(String folder) {
        return project + "/" + folder;
    }


}
