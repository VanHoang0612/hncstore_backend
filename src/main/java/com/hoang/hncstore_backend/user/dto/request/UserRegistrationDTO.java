package com.hoang.hncstore_backend.user.dto.request;

public record UserRegistrationDTO(
        String phoneNumber,
        String password
) {
}
