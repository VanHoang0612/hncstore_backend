package com.example.hcnstore_backend.core.enums;


import com.example.hcnstore_backend.core.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CommonCode implements ResponseCode {
    SUCCESS("success", "SUCCESS", HttpStatus.OK),
    VALIDATION_FAIL("validation.failed", "VAL-01", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("internal.server.error", "ERR-01", HttpStatus.INTERNAL_SERVER_ERROR),
    //    Success
    CREATE_SUCCESS("success.create", "CREATE_SUCCESS", HttpStatus.CREATED),
    UPDATE_SUCCESS("success.update", "UPDATE_SUCCESS", HttpStatus.OK),
    DELETE_SUCCESS("success.delete", "DELETE_SUCCESS", HttpStatus.NO_CONTENT),
    GET_SUCCESS("success.get", "GET_SUCCESS", HttpStatus.OK),
    GET_ALL_SUCCESS("success.get-all", "GET_ALL_SUCCESS", HttpStatus.OK),
    GET_UPLOAD_SIGNATURE("success.get-upload-signature", "GET_UPLOAD_SIGNATURE", HttpStatus.OK),
    //    Error
    NOT_FOUND("error.not_found", "NOT_FOUND", HttpStatus.NOT_FOUND),
    NOT_FOUND_WITH("error.not_found_with", "NOT_FOUND_WITH_ID", HttpStatus.NOT_FOUND),
    ALREADY_EXIST("error.already_exist", "ALREADY_EXIST", HttpStatus.CONFLICT),
    ALREADY_EXIST_WITH("error.already_exist_with", "ALREADY_EXIST", HttpStatus.CONFLICT),
    FILE_NOT_FOUND_IN_CLOUD("error.file_not_found_in_cloud", "FILE_NOT_FOUND_IN_CLOUD", HttpStatus.NOT_FOUND),
    INVALID_FILE("error.invalid_file", "INVALID_FILE", HttpStatus.BAD_REQUEST),
    ;

    private final String messageKey;
    private final String code;
    private final HttpStatus httpStatus;

    @Getter
    private static final String labelDataType = "label.data_type";

}
