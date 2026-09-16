package com.example.hcnstore_backend.core.response;

public record ErrorDetail(
        String field,
        String message
) {
}
