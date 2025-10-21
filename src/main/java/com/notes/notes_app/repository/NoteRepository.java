package com.notes.notes_app.repository;

import com.notes.notes_app.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
