package org.smirnowku.hwsc.core.exception;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends BaseException {

    public IllegalArgumentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public IllegalArgumentException(String message, String developerMessage) {
        super(message, developerMessage, HttpStatus.BAD_REQUEST);
    }
}
