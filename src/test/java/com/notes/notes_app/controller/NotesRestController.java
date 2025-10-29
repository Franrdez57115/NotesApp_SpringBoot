package com.notes.notes_app.controller;

import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.NoteRepository;
import com.notes.notes_app.repository.service.NoteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class NotesRestController {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NoteRepository noteRepository;

    @Test
    void crearNuevaNota() throws Exception{

        String nuevaNotaJson = """
                {
                    "title": "Nota nueva",
                    "description": "Test Funcional"
                }
                """;

        mockMvc.perform(post("/notes/new")
                        .with(user("testUser").roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nuevaNotaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Funcional"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void borrarNota() throws Exception{
        Note note = new Note();
        note.setTitle("Nota a eliminar");
        note.setDescription("test funcional");
         noteRepository.save(note);

        mockMvc.perform(delete("/notes/{id}", note.getId())
                        .with(csrf())
                        .with(user("testUser").roles("USER")))
                .andExpect(status().isOk());

        boolean exists = noteRepository.existsById(note.getId());
        assertFalse(exists);

    }

    @Test
    void verNotas() throws Exception{

        mockMvc.perform(get("/notes")
                        .with(user("testUser").roles("USER")))
                .andExpect(status().isOk());
    }
}
