package com.hanheum.backend.global.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinessStatus businessStatus;
    private final String message;

    public BusinessException(BusinessStatus businessStatus) {
        super(businessStatus.getMessage());
        this.message = businessStatus.getMessage();
        this.businessStatus = businessStatus;
    }

    public BusinessException(BusinessStatus businessStatus, String message) {
        super(message);
        this.message = message;
        this.businessStatus = businessStatus;
    }

}
