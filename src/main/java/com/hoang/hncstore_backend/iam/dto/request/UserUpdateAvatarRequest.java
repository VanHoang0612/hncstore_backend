package com.hoang.hncstore_backend.iam.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserUpdateAvatarRequest(
        MultipartFile avatarFile
) {
}
