package com.hoang.hncstore_backend.storage.exception;

import com.hoang.hncstore_backend.core.exception.BaseException;
import com.hoang.hncstore_backend.core.response.ResponseCode;

public class StorageException extends BaseException {

    public StorageException(ResponseCode responseCode, Object... args) {
        super(responseCode, args);
    }

}
