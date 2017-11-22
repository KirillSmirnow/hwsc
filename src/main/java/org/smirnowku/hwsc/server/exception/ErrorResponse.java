package org.smirnowku.hwsc.server.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

    private final HttpStatus httpStatus;
    private final String message;
    private final String developerMessage;

    public ErrorResponse(HttpStatus httpStatus, String message, String developerMessage) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.developerMessage = developerMessage;
    }

    public HttpStatus httpStatus() {
        return httpStatus;
    }

    public int getStatus() {
        return httpStatus.value();
    }

    public String getError() {
        return httpStatus.getReasonPhrase();
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
