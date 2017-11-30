package org.smirnowku.hwsc.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends HwscException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }

    public ForbiddenException(String message, String developerMessage) {
        super(HttpStatus.FORBIDDEN, message, developerMessage);
    }
}
