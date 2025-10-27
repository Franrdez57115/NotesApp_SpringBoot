package com.notes.notes_app.repository.service;

import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestNoteService {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void devolverTodasNotas() {
        Note note1 = new Note();
        note1.setTitle("Note 1");
        note1.setDescription("Prueba nota 1");
        Note note2 = new Note();
        note2.setTitle("Note 2");
        note2.setDescription("Prueba nota 2");

        List<Note> notasSimuladas = Arrays.asList(note1, note2);

        when (noteRepository.findAll()).thenReturn(notasSimuladas);

        List<Note> notes = noteService.listNotes();
        assertEquals(2, notes.size());
    }


}
