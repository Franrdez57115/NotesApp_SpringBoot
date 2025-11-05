package com.notes.notes_app.controller;

import com.notes.notes_app.exception.NoteNotFoundException;
import com.notes.notes_app.model.Note;
import com.notes.notes_app.repository.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private NoteService noteService;

    @Autowired
    public NotesController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String listNotes(Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
          model.addAttribute("notes", noteService.listNotesFor(authUser.getUsername()));
          return "index";
    }

    @PostMapping("/new")
    @ResponseBody
    public Note createNote(@RequestBody Note note, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        return noteService.saveNoteFor(note, authUser.getUsername());
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Note updateNote(@PathVariable Long id, @RequestBody Note updatedNote, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        Note existing = noteService.findByIdFor(id, authUser.getUsername()).orElseThrow(() -> new NoteNotFoundException(id));
        updatedNote.setId(id);
        updatedNote.setDate(existing.getDate());
        return noteService.saveNoteFor(updatedNote, authUser.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteNote(@PathVariable Long id, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        noteService.deleteNoteFor(id, authUser.getUsername());
    }

    @GetMapping("/view/{id}")
    public String viewNote(@PathVariable Long id, Model model, @AuthenticationPrincipal org.springframework.security.core.userdetails.User authUser) {
        Note note = noteService.findByIdFor(id, authUser.getUsername())
                .orElseThrow(() -> new NoteNotFoundException(id));
        model.addAttribute("note", note);
        return "view";
    }


}
