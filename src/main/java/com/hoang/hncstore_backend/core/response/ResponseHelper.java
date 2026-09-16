package com.hoang.hncstore_backend.core.response;

import com.hoang.hncstore_backend.core.hepler.Translator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResponseHelper {
    private final Translator translator;

    public <T> ResponseEntity<ApiResponse<T>> buildSuccess(ResponseCode responseCode, String labelKey, T data,
                                                           Object... args) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(
                        ApiResponse.success(responseCode, translator.getMessage(responseCode, labelKey, args), data)
                );
    }


    public <T> ResponseEntity<ApiResponse<T>> buildFailure(ResponseCode responseCode, List<ErrorDetail> errorDetails,
                                                           Object... args) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(
                        ApiResponse.failure(responseCode, translator.getMessage(responseCode, null, args), errorDetails)
                );
    }

    public <T> ResponseEntity<ApiResponse<T>> buildFailure(ResponseCode responseCode, String labelKey,
                                                           List<ErrorDetail> errorDetails, Object... args) {
        return ResponseEntity.status(responseCode.getHttpStatus())
                .body(
                        ApiResponse.failure(responseCode, translator.getMessage(responseCode, labelKey, args),
                                errorDetails)
                );
    }
}
