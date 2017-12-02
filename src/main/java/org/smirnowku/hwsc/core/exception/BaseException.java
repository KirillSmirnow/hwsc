package org.smirnowku.hwsc.core.exception;

import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException {

    private final String developerMessage;
    private final HttpStatus httpStatus;

    public BaseException(String message, HttpStatus httpStatus) {
        this(message, message, httpStatus);
    }

    public BaseException(String message, String developerMessage, HttpStatus httpStatus) {
        super(message);
        this.developerMessage = developerMessage;
        this.httpStatus = httpStatus;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
