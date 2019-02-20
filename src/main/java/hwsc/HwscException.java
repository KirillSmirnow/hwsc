package hwsc;

import lombok.Getter;

@Getter
public class HwscException extends RuntimeException {

    private final String userMessage;
    private final String developerMessage;

    public HwscException(String userMessage) {
        this(userMessage, userMessage);
    }

    public HwscException(String userMessage, String developerMessage) {
        super(userMessage);
        this.userMessage = userMessage;
        this.developerMessage = developerMessage;
    }
}
