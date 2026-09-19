package com.hoang.hncstore_backend.iam.enums;

import com.hoang.hncstore_backend.core.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AuthCode implements ResponseCode {
    TOKEN_EXPIRED("auth.error.token_expired", "TOKEN-EXPIRED", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("auth.error.unauthorized", "UNAUTHORIZED", HttpStatus.UNAUTHORIZED),
    ;

    private final String messageKey;
    private final String code;
    private final HttpStatus httpStatus;
}
