package com.randomapps.battlefield.exception;

public class GameInitializationException extends Exception {
    public GameInitializationException(Throwable cause) {
        super(cause);
    }

    public GameInitializationException(String message) {
        super(message);
    }
}
