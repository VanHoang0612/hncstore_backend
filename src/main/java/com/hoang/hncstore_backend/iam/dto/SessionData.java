package com.hoang.hncstore_backend.iam.dto;

import lombok.Builder;
import lombok.With;


@Builder
@With
public record SessionData(
        String contact,
        String otp,
        String purpose,
        boolean verified
) {

}
