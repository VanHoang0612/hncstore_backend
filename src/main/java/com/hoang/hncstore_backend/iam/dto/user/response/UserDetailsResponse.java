package com.hoang.hncstore_backend.iam.dto.user.response;

public record UserDetailsResponse(
        String id,
        String phoneNumber,
        String email,
        String fullName,
        String gender,
        String avatarUrl,
        String status
) {
}
