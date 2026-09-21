package com.hoang.hncstore_backend.iam.dto.user.response;

public record UserResponse(
        String id,
        String phoneNumber,
        String email,
        String fullName,
        String gender,
        String avatarUrl,
        String status

) {

}
