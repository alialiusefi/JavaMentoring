package com.epam.esm.exception;

/**
 * Exception is thrown when database errors where detected
 * or unauthorized servlet access.
 */
public class PersistentException extends RuntimeException {

    public PersistentException() { }

    public PersistentException(Throwable cause) {
        super(cause);
    }

    public PersistentException(String message) {
        super(message);
    }

    public PersistentException(String message, Throwable cause) {
        super(message, cause);
    }
}
