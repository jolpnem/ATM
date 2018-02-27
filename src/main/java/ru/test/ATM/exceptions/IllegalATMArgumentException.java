package ru.test.ATM.exceptions;

public class IllegalATMArgumentException extends IllegalArgumentException {
    public IllegalATMArgumentException() {
        super();
    }

    public IllegalATMArgumentException(String s) {
        super(s);
    }

    public IllegalATMArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalATMArgumentException(Throwable cause) {
        super(cause);
    }
}
