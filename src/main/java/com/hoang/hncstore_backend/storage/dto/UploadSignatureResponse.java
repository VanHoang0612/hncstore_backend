package com.hoang.hncstore_backend.storage.dto;

import lombok.Builder;

@Builder
public record UploadSignatureResponse(
        String folder,
        String publicId,
        String signature,
        long timestamp,
        String uploadUrl,
        String apiKey
) {

}
