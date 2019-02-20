package hwsc;

import org.springframework.http.HttpStatus;

public class HwscException extends RuntimeException {

    private final String developerMessage;
    private final HttpStatus httpStatus;

    public HwscException(String message) {
        this(message, message, HttpStatus.BAD_REQUEST);
    }

    public HwscException(String message, String developerMessage) {
        this(message, developerMessage, HttpStatus.BAD_REQUEST);
    }

    public HwscException(String message, HttpStatus httpStatus) {
        this(message, message, httpStatus);
    }

    public HwscException(String message, String developerMessage, HttpStatus httpStatus) {
        super(message);
        this.developerMessage = developerMessage;
        this.httpStatus = httpStatus;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
