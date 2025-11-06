package com.notes.notes_app.controller;

import com.notes.notes_app.model.AppUser;
import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.AppUserRepository;
import com.notes.notes_app.repository.NoteRepository;
import com.notes.notes_app.repository.service.NoteService;
import org.junit.jupiter.api.BeforeEach;
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
    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser testUser;
    private AppUser otherUser;

    @BeforeEach
    void setUp() {
        testUser = new AppUser();
        testUser.setUsername("test");
        testUser.setPassword("password");
        appUserRepository.save(testUser);

        otherUser = new AppUser();
        otherUser.setUsername("other");
        otherUser.setPassword("password");
        appUserRepository.save(otherUser);
    }

    @Test
    void crearNuevaNota() throws Exception {
        String nuevaNotaJson = """
                {
                    "title": "Nota nueva",
                    "description": "Test Funcional"
                }
                """;

        mockMvc.perform(post("/notes/new")
                        .with(user(testUser.getUsername()).roles("USER"))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(nuevaNotaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("Test Funcional"))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.owner.username").value("testUser"));
    }

    @Test
    void borrarNota() throws Exception {
        Note note = new Note();
        note.setTitle("Nota a eliminar");
        note.setDescription("Test Funcional");
        note.setOwner(testUser);
        noteRepository.save(note);

        mockMvc.perform(delete("/notes/{id}", note.getId())
                        .with(user(testUser.getUsername()).roles("USER"))
                        .with(csrf()))
                .andExpect(status().isOk());

        assertFalse(noteRepository.existsById(note.getId()));
    }
}
