package com.hoang.hncstore_backend.storage.internal.service;

import com.hoang.hncstore_backend.storage.StorageModuleApi;
import com.hoang.hncstore_backend.storage.dto.UploadSignatureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class StorageModuleApiImpl implements StorageModuleApi {
    private final CloudinaryService cloudinaryService;

    @Override
    public Map<String, Object> uploadImage(MultipartFile file, String folder, String publicId) {
        return cloudinaryService.uploadImage(file, folder, publicId);
    }

    @Override
    public void deleteImage(String publicId) {
        cloudinaryService.deleteImage(publicId);
    }

    @Override
    public String getImageUrl(String publicId, String version) {
        return cloudinaryService.getImageUrl(publicId, version);
    }

    @Override
    public UploadSignatureResponse generateUploadSignature(String folder, String publicId) {
        return cloudinaryService.generateUploadSignature(folder, publicId);
    }

    @Override
    public void verifyFile(String publicId, String version) {
        cloudinaryService.verifyFile(publicId, version);
    }
}
  
