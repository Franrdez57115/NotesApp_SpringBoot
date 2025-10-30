package com.notes.notes_app.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {
        super("No se ha podido encontrar la nota con Id: " + id);
    }
}
