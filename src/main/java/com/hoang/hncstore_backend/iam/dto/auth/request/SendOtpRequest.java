package com.hoang.hncstore_backend.iam.dto.auth.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SendOtpRequest(
        @NotBlank(message = "validation.not_blank")
        @Size(min = 10, max = 10, message = "validation.size")
        String phoneNumber
) {
}
