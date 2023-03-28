package com.hanheum.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BusinessStatus {
    EMAIL_ALREADY_EXIST(HttpStatus.CONFLICT, "USR0001", "이미 존재하는 이메일입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "ETC0001","잘못된 요청입니다."),
    OTHER_UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "OTH0001", "예기치 못한 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
