
package com.hanheum.backend.global.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponseDto {
    // 예외 발생 시 ResponseEntity 객체에 담을 Body Dto 클래스
    // 클라이언트 사이드에서 이 클래스의 필드들을 JSON 형태로 받게 됨
    private final int status;
    private final String message;
    private final String code;

    @Builder(builderClassName = "Business", builderMethodName = "business")
    public ExceptionResponseDto(BusinessException exception) {
        this.status = exception.getBusinessStatus().getStatus().value();
        this.message = exception.getMessage();
        this.code = exception.getBusinessStatus().getCode();
    }

    @Builder(builderClassName = "Unknown", builderMethodName = "unknown")
    public ExceptionResponseDto(int status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}