package com.notes.notes_app.repository.service;

import com.notes.notes_app.exception.DuplicateUsernameException;
import com.notes.notes_app.model.AppUser;
import com.notes.notes_app.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    public AuthService(AppUserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public void register(String username, String password) throws DuplicateUsernameException {
        if (repo.existsByUsername(username)) {
            throw new DuplicateUsernameException("El usuario ya existe, prueba otro");
        }
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setRole("ROLE_USER");
        repo.save(user);
    }
}
