package com.notes.notes_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Excepción para captar una nota que no existe.
    @ExceptionHandler(NoteNotFoundException.class)
    public ModelAndView handleNoteNotFound(NoteNotFoundException ex) {

        ModelAndView mav = new ModelAndView("error-404");

        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("status", HttpStatus.NOT_FOUND.value());
        mav.addObject("timestamp", LocalDateTime.now());

        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }

    // Excepción para captar que datos obligatorios no esten vacíos.
    @ExceptionHandler(InvalidNoteDataException.class)
    public ModelAndView handleInvalidNoteData(InvalidNoteDataException ex) {

        ModelAndView mav = new ModelAndView("error-400");

        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("status", HttpStatus.BAD_REQUEST.value());
        mav.addObject("timestamp", LocalDateTime.now());
        mav.setStatus(HttpStatus.BAD_REQUEST);
        return mav;
    }
}
