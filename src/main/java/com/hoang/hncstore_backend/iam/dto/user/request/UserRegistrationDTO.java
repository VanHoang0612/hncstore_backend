package com.hoang.hncstore_backend.iam.dto.user.request;

public record UserRegistrationDTO(
        String phoneNumber,
        String password
) {
}
