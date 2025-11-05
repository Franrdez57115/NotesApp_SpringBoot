package com.notes.notes_app.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String autor;

    private String color = "#ffffff";

    @Enumerated(EnumType.STRING)
    private State state;

    private LocalDate date;

    // Relación
    // Añado propietario nota. Relación muchos a uno, cada nota pertenece a un AppUser.
    // Lazy: Para carga perezosa...Al leer una nota, si a priori no necesitas el dueño, la bbdd solo te trae la nota.
    // Ventajas: rendimiento (menos datos si no necesitas el dueño).
    // Y por último mapeo la columna FK owner_id en la tala note. Si quisieras obligar a que haya dueño podemos: nullable=false
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private AppUser owner;

    @PrePersist
    protected void onCreate() {
        date = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = titulo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descripcion) {
        this.description = descripcion;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public State getState(){
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AppUser getOwner() {
        return owner;
    }

    public void setOwner(AppUser owner) {
        this.owner = owner;
    }
}
