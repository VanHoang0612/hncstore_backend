package com.hoang.hncstore_backend.user.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record UserUpdateAvatarRequest(
        MultipartFile avatarFile
) {
}
