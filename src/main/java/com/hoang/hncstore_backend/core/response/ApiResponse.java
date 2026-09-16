package com.hoang.hncstore_backend.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        ZonedDateTime timestamp,
        boolean success,
        String code,
        String message,
        List<ErrorDetail> details,
        T data
) {
    public static <T> ApiResponse<T> success(ResponseCode responseCode, String translatedMessage, T data) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now(ZoneOffset.UTC))
                .success(true)
                .code(responseCode.getCode())
                .message(translatedMessage)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> failure(ResponseCode responseCode, String translatedMessage,
                                             List<ErrorDetail> errorDetails) {
        return ApiResponse.<T>builder()
                .timestamp(ZonedDateTime.now(ZoneOffset.UTC))
                .success(false)
                .code(responseCode.getCode())
                .message(translatedMessage)
                .details(errorDetails)
                .build();
    }
}

