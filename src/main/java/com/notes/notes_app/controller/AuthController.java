package com.notes.notes_app.controller;

import com.notes.notes_app.exception.DuplicateUsernameException;
import com.notes.notes_app.model.AppUser;
import com.notes.notes_app.repository.AppUserRepository;
import com.notes.notes_app.repository.service.AuthService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Validated
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Muestra formulario registro
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // Procesar el registro
    @PostMapping("/register")
    public String register(@RequestParam @NotBlank String username,
                           @RequestParam @NotBlank String password,
                           Model model) {
        //Validación si el usuario existe
        try {
            authService.register(username, password);
            return "redirect:/login?registered";
        } catch (DuplicateUsernameException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
