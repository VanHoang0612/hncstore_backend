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
    REGISTER_SUCCESS("auth.success.register", "REGISTER-SUCCESS", HttpStatus.OK),
    REGISTERED("auth.error.registered", "REGISTERED", HttpStatus.BAD_REQUEST),
    SEND_OTP_SUCCESS("auth.success.send_otp", "SEND_OTP_SUCCESS", HttpStatus.OK),
    OTP_EXPIRED("auth.error.otp_expired", "OTP-EXPIRED", HttpStatus.BAD_REQUEST),
    OTP_INVALID("auth.error.otp_invalid", "OTP-INVALID", HttpStatus.BAD_REQUEST),
    SESSION_NOT_VERIFIED("auth.error.session_not_verified", "SESSION_NOT_VERIFIED", HttpStatus.BAD_REQUEST),
    ;

    private final String messageKey;
    private final String code;
    private final HttpStatus httpStatus;
}
