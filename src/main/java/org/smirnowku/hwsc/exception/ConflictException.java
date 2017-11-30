package org.smirnowku.hwsc.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends HwscException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

    public ConflictException(String message, String developerMessage) {
        super(HttpStatus.CONFLICT, message, developerMessage);
    }
}
