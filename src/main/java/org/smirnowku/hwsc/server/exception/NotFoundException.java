package org.smirnowku.hwsc.server.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HwscException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(String message, String developerMessage) {
        super(HttpStatus.NOT_FOUND, message, developerMessage);
    }
}
