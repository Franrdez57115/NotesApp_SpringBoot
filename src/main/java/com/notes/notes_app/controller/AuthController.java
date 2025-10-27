package com.notes.notes_app.controller;

import com.notes.notes_app.model.AppUser;
import com.notes.notes_app.repository.AppUserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Validated
public class AuthController {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public AuthController(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Muestra formulario registro
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new RegisterDto ("",""));
        return "register";
    }

    // Procesar el registro
    @PostMapping("/register")
    public String register(@ModelAttribute("userDto") @Validated RegisterDto dto, Model model) {
        //Validación si el usuario existe
        if (repo.existsByUsername(dto.username)) {
            model.addAttribute("error", "El usuario ya existe");
            return "register";
        }
        AppUser user = new AppUser();
        user.setUsername(dto.username);
        user.setPassword(encoder.encode(dto.password));
        user.setRole("ROLE_USER");

        repo.save(user);
        return "redirect:/login?registered";
    }

    public record RegisterDto (@NotBlank String username, @NotBlank String password) {

    }
}
