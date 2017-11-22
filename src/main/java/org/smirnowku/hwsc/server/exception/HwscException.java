package org.smirnowku.hwsc.server.exception;

import org.springframework.http.HttpStatus;

public class HwscException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public HwscException(HttpStatus httpStatus, String message) {
        this(httpStatus, message, message);
    }

    public HwscException(HttpStatus httpStatus, String message, String developerMessage) {
        super(developerMessage);
        this.errorResponse = new ErrorResponse(httpStatus, message, developerMessage);
    }

    public ErrorResponse errorResponse() {
        return errorResponse;
    }

    public HttpStatus httpStatus() {
        return errorResponse.httpStatus();
    }
}
