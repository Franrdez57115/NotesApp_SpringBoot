package com.notes.notes_app.repository.service;

import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> listNotes() {
        return noteRepository.findAll();
    }

    public Note saveNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("No existe esta Nota");
        } else {
            noteRepository.deleteById(id);
        }
    }

    public Optional<Note> findById(Long id) {
        return noteRepository.findById(id);
    }



}
