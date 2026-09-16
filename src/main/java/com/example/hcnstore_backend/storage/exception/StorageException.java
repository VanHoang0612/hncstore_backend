package com.example.hcnstore_backend.storage.exception;

import com.example.hcnstore_backend.core.exception.BaseException;
import com.example.hcnstore_backend.core.response.ResponseCode;

public class StorageException extends BaseException {

    public StorageException(ResponseCode responseCode, Object... args) {
        super(responseCode, args);
    }

}
