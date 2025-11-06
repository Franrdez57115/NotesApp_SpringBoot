package com.notes.notes_app.repository.service;

import com.notes.notes_app.model.AppUser;
import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.AppUserRepository;
import com.notes.notes_app.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestNoteService {
    @Mock
    private NoteRepository noteRepository;

    @Mock
    private AppUserRepository appUserRepository;

    @InjectMocks
    private NoteService noteService;

    private final String USERNAME = "usuario1";

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

        when (noteRepository.findByOwnerUsername(USERNAME)).thenReturn(notasSimuladas);

        List<Note> notes = noteService.listNotesFor(USERNAME);
        assertEquals(2, notes.size());
        verify(noteRepository).findByOwnerUsername(USERNAME);
    }

    // Test unitario de Guardado de notas correctamente
    @Test
    void guardarNota() {
        AppUser appUser = new AppUser();
        appUser.setUsername(USERNAME);

        Note note = new Note();
        note.setTitle("Note 1");
        note.setDescription("Prueba nota 1");

        when(appUserRepository.findByUsername(USERNAME)).thenReturn(Optional.of(appUser));
        when(noteRepository.save(note)).thenReturn(note);

        Note result = noteService.saveNoteFor(note, USERNAME);

        assertEquals("Note 1", result.getTitle());
        verify(appUserRepository).findByUsername(USERNAME);
        verify(noteRepository).save(note);
    }

    // Test unitario de nota existente e inexistente
    @Test
    void buscarNotaPorId_existente() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Nota existente");

        when(noteRepository.findByIdAndOwnerUsername(1L, USERNAME)).thenReturn(Optional.of(note));
        Optional<Note> resultado = noteService.findByIdFor(1L, USERNAME);

        assertTrue(resultado.isPresent());
        assertEquals("Nota existente", resultado.get().getTitle());
    }

    @Test
    void buscarNotaPorId_inexistente() {
        when(noteRepository.findByIdAndOwnerUsername(2L, USERNAME)).thenReturn(Optional.empty());
        Optional<Note> resultado = noteService.findByIdFor(2L, USERNAME);
        assertFalse(resultado.isPresent());
    }

    // Test unitario de eliminar notas existente e inexistente
    @Test
    void eliminarNota_existente() {
        Note note = new Note();
        note.setId(1L);
        note.setTitle("Nota existente");

        when(noteRepository.findByIdAndOwnerUsername(1L, USERNAME)).thenReturn(Optional.of(note));

        noteService.deleteNoteFor(1L, USERNAME);

        verify(noteRepository).findByIdAndOwnerUsername(1L, USERNAME);
        verify(noteRepository).delete(note);
    }

    @Test
    void eliminarNota_noExistente_debeLanzarExcepcion() {
        when(noteRepository.findByIdAndOwnerUsername(99L, USERNAME)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> noteService.deleteNoteFor(99L, USERNAME));

        verify(noteRepository).findByIdAndOwnerUsername(99L, USERNAME);
        verify(noteRepository, never()).delete(any());
    }

}
