package com.hoang.hncstore_backend.core.response;

public record ErrorDetail(
        String field,
        String message
) {
}
