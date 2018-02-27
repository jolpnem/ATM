package ru.test.ATM.exceptions;

public class InsufficientFundsException extends IllegalATMStateException {
    public InsufficientFundsException() {
        super();
    }

    public InsufficientFundsException(String s) {
        super(s);
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsufficientFundsException(Throwable cause) {
        super(cause);
    }
}
