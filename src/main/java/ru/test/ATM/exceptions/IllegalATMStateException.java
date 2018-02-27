package ru.test.ATM.exceptions;

public class IllegalATMStateException extends IllegalStateException {
    public IllegalATMStateException() {
        super();
    }

    public IllegalATMStateException(String s) {
        super(s);
    }

    public IllegalATMStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalATMStateException(Throwable cause) {
        super(cause);
    }
}
