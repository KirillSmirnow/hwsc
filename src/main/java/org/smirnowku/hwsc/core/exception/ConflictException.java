package org.smirnowku.hwsc.core.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends BaseException {

    public ConflictException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public ConflictException(String message, String developerMessage) {
        super(message, developerMessage, HttpStatus.CONFLICT);
    }
}
