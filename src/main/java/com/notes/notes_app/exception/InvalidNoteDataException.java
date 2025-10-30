package com.notes.notes_app.exception;

public class InvalidNoteDataException extends RuntimeException {
    public InvalidNoteDataException(String message) {
        super(message);
    }
}
