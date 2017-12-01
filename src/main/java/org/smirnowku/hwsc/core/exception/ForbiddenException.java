package org.smirnowku.hwsc.core.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message, String developerMessage) {
        super(message, developerMessage, HttpStatus.FORBIDDEN);
    }
}
