package com.hoang.hncstore_backend.core.response;

import org.springframework.http.HttpStatus;

public interface ResponseCode {


    String getMessageKey();

    String getCode();

    HttpStatus getHttpStatus();
}
