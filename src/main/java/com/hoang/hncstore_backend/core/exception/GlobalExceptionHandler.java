package com.hoang.hncstore_backend.core.exception;

import com.hoang.hncstore_backend.core.enums.CommonCode;
import com.hoang.hncstore_backend.core.hepler.Translator;
import com.hoang.hncstore_backend.core.response.ApiResponse;
import com.hoang.hncstore_backend.core.response.ErrorDetail;
import com.hoang.hncstore_backend.core.response.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final Translator translator;
    private final ResponseHelper responseHelper;


    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException ex) {
//        List<ErrorDetail> translatedDetails;
//        if (ex.getErrorDetails()
//                .isEmpty()) {
//            translatedDetails = List.of(new ErrorDetail(null, translatedMessage));
//        } else {
//            translatedDetails = ex.getErrorDetails()
//                    .stream()
//                    .map(
//                            rawDetail -> new ErrorDetail(rawDetail.field(), translator.getMessage(rawDetail.message
//                            ()))
//                    )
//                    .toList();
//        }
        String labelKey = ex.getLabelKey();
        return responseHelper.buildFailure(ex.getResponseCode(), labelKey, null, ex.getArgs());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return responseHelper.buildFailure(CommonCode.INTERNAL_SERVER_ERROR, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    String field = fieldError.getField();
                    Object[] args = fieldError.getArguments();
                    log.info("field: {}, args: {}", field, args);
                    String translatedMessage = translator.getMessage(fieldError.getDefaultMessage(), null, args);
                    return new ErrorDetail(field, translatedMessage);
                })
                .sorted(Comparator.comparing(ErrorDetail::field))
                .toList();
        return responseHelper.buildFailure(CommonCode.VALIDATION_FAIL, errorDetails);
    }
}
