package com.hanheum.backend.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ExceptionResponseDto> handleBusinessException(BusinessException e) {
        log.error("{}", e);
        ExceptionResponseDto response = ExceptionResponseDto.business()
                .exception(e)
                .build();

        return ResponseEntity
                .status(e.getBusinessStatus().getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ExceptionResponseDto> handleServerException(Exception e) {
        log.error("{}", e);
        ExceptionResponseDto response = ExceptionResponseDto.unknown()
                .status(BusinessStatus.OTHER_UNKNOWN.getStatus().value())
                .message(BusinessStatus.OTHER_UNKNOWN.getMessage())
                .code(BusinessStatus.OTHER_UNKNOWN.getCode())
                .build();

        return ResponseEntity
                .status(BusinessStatus.OTHER_UNKNOWN.getStatus())
                .body(response);
    }

}
