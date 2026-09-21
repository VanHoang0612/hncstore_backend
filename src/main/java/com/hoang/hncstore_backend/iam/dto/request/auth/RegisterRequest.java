package com.hoang.hncstore_backend.iam.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "validation.not_blank")
        @Size(min = 10, max = 10, message = "validation.size")
        String phoneNumber
) {

}
