package com.notes.notes_app.model;

import jakarta.persistence.*;

@Entity
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String role;

    // Relación
    // Relación uno a muchos
    // Aquí indicamos que el propietario está en la entidad Note en el campo "owner"
    // Consecuencia: La FK(owner_id) "vive" en la tabla note.
    // Cascade: propaga operaciones del padrea las hijas (como guardar, actualizar, borrar)
    // Ejemplo: si guardas un AppUser con notes nuevas, se crean las notas; si borras el AppUser, se borran sus notas.
    // orphanRemoval = true --> Si una Note deja de estar en la colección  se considera huérfana y JPA emite un DELETE de esa fila.
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private java.util.List<Note> notes = new java.util.ArrayList<>();

    public AppUser() {}

    public AppUser(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
