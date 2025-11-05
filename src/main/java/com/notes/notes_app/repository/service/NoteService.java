package com.notes.notes_app.repository.service;

import com.notes.notes_app.exception.InvalidNoteDataException;
import com.notes.notes_app.model.AppUser;
import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.AppUserRepository;
import com.notes.notes_app.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private NoteRepository noteRepository;
    private AppUserRepository userRepository;

    public NoteService(NoteRepository noteRepository, AppUserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public List<Note> listNotesFor(String username) {
        return noteRepository.findByOwnerUsername(username);
    }

    public Optional<Note> findByIdFor(Long id, String username) {
        return noteRepository.findByIdAndOwnerUsername(id, username);
    }

    public Note saveNoteFor(Note note, String username) {
        AppUser owner = userRepository.findByUsername(username).orElseThrow();
        note.setOwner(owner);
        if (note.getTitle() == null || note.getTitle().isBlank()) {
            throw new InvalidNoteDataException("El titulo de la nota no puede estar vacío.");
        }
        return noteRepository.save(note);
    }

    public void deleteNoteFor(Long id, String username) {
//        if (!noteRepository.existsById(id)) {
//            throw new NoteNotFoundException(id);
//        } else {
//            noteRepository.deleteById(id);
//        }
        Note note = noteRepository.findByIdAndOwnerUsername(id, username).orElseThrow();
        noteRepository.delete(note);
    }

}
