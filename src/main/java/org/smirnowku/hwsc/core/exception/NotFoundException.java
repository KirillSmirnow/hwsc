package org.smirnowku.hwsc.core.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, String developerMessage) {
        super(message, developerMessage, HttpStatus.NOT_FOUND);
    }
}
