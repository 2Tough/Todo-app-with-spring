package com.example.todoappwithspring.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * ENTIDAD: Representa una TAREA en la base de datos
 *
 * Anotaciones importantes:
 * @Entity - Le dice a Spring que esta clase es una tabla
 * @Table - Define el nombre de la tabla en la BD
 */
@Entity
@Table(name = "tasks")
public class Task {

    /**
     * ID: Clave primaria de la tabla
     * @Id - Marca este campo como clave primaria
     * @GeneratedValue - El ID se genera automáticamente (auto-increment)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * TITLE: Título de la tarea
     * @Column - Define propiedades de la columna
     * nullable=false - Este campo es OBLIGATORIO
     */
    @Column(nullable = false)
    private String title;

    /**
     * DESCRIPTION: Descripción de la tarea (opcional)
     */
    private String description;

    /**
     * COMPLETED: Estado de la tarea (completada o no)
     * Por defecto es false (pendiente)
     */
    @Column(nullable = false)
    private boolean completed = false;

    /**
     * CREATED_AT: Fecha de creación
     * @Column con name - El nombre en la BD será "created_at"
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * @PrePersist - Se ejecuta ANTES de guardar en la BD
     * Automáticamente establece la fecha de creación
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // ==========================================
    // CONSTRUCTORES
    // ==========================================

    /**
     * Constructor vacío - REQUERIDO por JPA
     */
    public Task() {
    }

    /**
     * Constructor con parámetros - Para crear tareas fácilmente
     */
    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // ==========================================
    // GETTERS Y SETTERS
    // Permiten acceder y modificar los campos
    // ==========================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * toString() - Para imprimir el objeto fácilmente
     */
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", createdAt=" + createdAt +
                '}';
    }
}