package org.zafu.spring_new.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1, "Key message invalid", HttpStatus.BAD_REQUEST),
    DOB_INVALID(18, "You must be at least {min}", HttpStatus.BAD_REQUEST),
    USER_EXISTED(10, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(15, "User not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(11, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(12, "Password must be in range of {min} and 16 characters", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH(13, "Incorrect password", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(14, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(16, "You have no permission", HttpStatus.FORBIDDEN);
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
}
