package com.notes.notes_app.controller;

import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private NoteService noteService;

    @Autowired
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String listNotes(Model model) {
          model.addAttribute("notes", noteService.listNotes());
          return "index";
    }

    @PostMapping("/api")
    @ResponseBody
    public Note createNote(@RequestBody Note note) {
        return noteService.saveNote(note);
    }

    @PutMapping("/api/{id}")
    @ResponseBody
    public Note updateNote(@PathVariable Long id, @RequestBody Note updatedNote) {
        Note existing = noteService.findById(id).orElseThrow(() -> new IllegalArgumentException("Nota no encontrada con ID: " + id));
        updatedNote.setId(id);
        updatedNote.setDate(existing.getDate());
        return noteService.saveNote(updatedNote);
    }

    @DeleteMapping("/api/{id}")
    @ResponseBody
    public void deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
    }

    @GetMapping("/view/{id}")
    public String viewNote(@PathVariable Long id, Model model) {
        Note note = noteService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nota no encontrada: " + id));
        model.addAttribute("note", note);
        return "view"; // templates/view.html
    }


}
