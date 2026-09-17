package com.hoang.hncstore_backend.iam.dto.request;

public record UserRegistrationDTO(
        String phoneNumber,
        String password
) {
}
