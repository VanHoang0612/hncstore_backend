package com.example.hcnstore_backend.core.exception;

import com.example.hcnstore_backend.core.response.ResponseCode;

public class BusinessException extends BaseException {
    public BusinessException(ResponseCode responseCode, String labelKey, Object... args) {
        super(responseCode, labelKey, args);
    }

    public BusinessException(ResponseCode responseCode, String labelKey, String fieldName, String value,
                             Object... args) {
        super(responseCode, labelKey, fieldName, value, args);
    }
}
