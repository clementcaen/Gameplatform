package fr.isencaen.gameplatform.exceptions;

public class IllegalClassException extends Exception {

    public IllegalClassException() {
    }

    public IllegalClassException(String message) {
        super(message);
    }

    public IllegalClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalClassException(Throwable cause) {
        super(cause);
    }

    public IllegalClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
