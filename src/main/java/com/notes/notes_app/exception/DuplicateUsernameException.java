package com.notes.notes_app.exception;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String message) {
        super(message);
    }
}
