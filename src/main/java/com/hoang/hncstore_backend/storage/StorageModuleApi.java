package com.hoang.hncstore_backend.storage;

import com.hoang.hncstore_backend.storage.dto.UploadSignatureResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface StorageModuleApi {
    Map<String, Object> uploadImage(MultipartFile file, String folder, String publicId);

    void deleteImage(String publicId);

    String getImageUrl(String publicId, String version);

    UploadSignatureResponse generateUploadSignature(String folder, String publicId);

    void verifyFile(String publicId, String version);
}
