package org.smirnowku.hwsc.exception;

import org.springframework.http.HttpStatus;

public class IllegalArgumentException extends HwscException {

    public IllegalArgumentException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public IllegalArgumentException(String message, String developerMessage) {
        super(HttpStatus.BAD_REQUEST, message, developerMessage);
    }
}
