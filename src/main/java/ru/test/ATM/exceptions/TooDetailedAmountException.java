package ru.test.ATM.exceptions;

public class TooDetailedAmountException extends IllegalATMArgumentException {
    public TooDetailedAmountException() {
        super();
    }

    public TooDetailedAmountException(String s) {
        super(s);
    }

    public TooDetailedAmountException(String message, Throwable cause) {
        super(message, cause);
    }

    public TooDetailedAmountException(Throwable cause) {
        super(cause);
    }
}
