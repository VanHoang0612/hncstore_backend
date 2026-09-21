package com.hoang.hncstore_backend.iam.dto.auth;

import lombok.Builder;

@Builder
public record OtpSession(
        String sessionId,
        String otp,
        String ttlInMinutes
) {

}
