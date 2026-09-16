package com.example.hcnstore_backend.storage.enums;

import com.example.hcnstore_backend.core.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StorageResponseCode implements ResponseCode {
    FILE_NOT_FOUND("error.file_not_found_in_cloud", "STORAGE-001", HttpStatus.NOT_FOUND),
    INVALID_FILE("error.invalid_file", "STORAGE-002", HttpStatus.BAD_REQUEST),
    ;

    private final String messageKey;
    private final String code;
    private final HttpStatus httpStatus;
}
