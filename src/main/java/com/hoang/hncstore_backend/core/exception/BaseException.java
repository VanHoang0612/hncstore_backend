package com.hoang.hncstore_backend.core.exception;

import com.hoang.hncstore_backend.core.response.ErrorDetail;
import com.hoang.hncstore_backend.core.response.ResponseCode;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class BaseException extends RuntimeException {
    private final ResponseCode responseCode;
    private final String labelKey;
    private final List<ErrorDetail> errorDetails;
    private final Object[] args;

    public BaseException(ResponseCode responseCode, Object... args) {
        super(responseCode.getMessageKey());
        this.responseCode = responseCode;
        this.labelKey = null;
        this.errorDetails = List.of();
        this.args = args;
    }

    public BaseException(ResponseCode responseCode, String labelKey, Object... args) {
        super(responseCode.getMessageKey());
        this.responseCode = responseCode;
        this.labelKey = labelKey;
        this.errorDetails = List.of();
        this.args = args;
    }

    public BaseException(ResponseCode responseCode, String labelKey, List<ErrorDetail> errorDetails, Object... args) {
        super(responseCode.getMessageKey());
        this.responseCode = responseCode;
        this.labelKey = labelKey;
        this.errorDetails = (errorDetails != null) ? errorDetails : List.of();
        this.args = args;

    }
}

