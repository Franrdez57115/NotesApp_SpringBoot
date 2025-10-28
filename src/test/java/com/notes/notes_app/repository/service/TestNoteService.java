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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestNoteService {
    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    // Test unitario de lista devuelta correctamente
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

    // Test unitario de Guardado de notas correctamente
    @Test
    void guardarNota() {
        Note note = new Note();
        note.setTitle("Note 1");
        note.setDescription("Prueba nota 1");

        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteService.saveNote(note);

        assertEquals("Note 1", result.getTitle());
        verify(noteRepository).save(note);
    }

    // Test unitario de nota existente e inexistente
    @Test
    void buscarNotaPorId_existente() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Nota existente");

        when(noteRepository.findById(1L)).thenReturn(Optional.of(note));
        Optional<Note> resultado = noteService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Nota existente", resultado.get().getTitle());
    }

    @Test
    void buscarNotaPorId_inexistente() {
        when(noteRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Note> resultado = noteService.findById(2L);
        assertFalse(resultado.isPresent());
    }

    // Test unitario de eliminar notas existente e inexistente
    @Test
    void eliminarNota_existente() {
        when(noteRepository.existsById(1L)).thenReturn(true);

        noteService.deleteNote(1L);

        verify(noteRepository).existsById(1L);
        verify(noteRepository).deleteById(1L);
    }

    @Test
    void eliminarNota_noExistente_debeLanzarExcepcion() {
        when(noteRepository.existsById(99L)).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> noteService.deleteNote(99L)
        );

        assertEquals("No existe esta Nota", exception.getMessage());
        verify(noteRepository).existsById(99L);
        verify(noteRepository, never()).deleteById(anyLong());
    }

}
