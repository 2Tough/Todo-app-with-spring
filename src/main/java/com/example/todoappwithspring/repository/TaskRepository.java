package com.example.todoappwithspring.repository;

import com.example.todoappwithspring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * REPOSITORY: Interfaz para acceder a la base de datos
 *
 * ¿Qué hace Spring aquí?
 * - Spring crea AUTOMÁTICAMENTE la implementación de esta interfaz
 * - No necesitas escribir código SQL
 * - Ya tienes métodos como: save(), findAll(), findById(), delete()
 *
 * JpaRepository<Task, Long> significa:
 * - Task: La entidad que manejamos
 * - Long: El tipo de dato del ID
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * MÉTODOS HEREDADOS DE JpaRepository (ya los tienes disponibles):
     *
     * - save(Task task)              → Guardar o actualizar una tarea
     * - findAll()                    → Obtener todas las tareas
     * - findById(Long id)            → Buscar por ID
     * - deleteById(Long id)          → Eliminar por ID
     * - count()                      → Contar tareas
     * - existsById(Long id)          → Verificar si existe
     *
     * ¡Y muchos más!
     */

    /**
     * MÉTODOS PERSONALIZADOS:
     * Spring genera automáticamente la consulta SQL basándose en el nombre del método
     */

    /**
     * Buscar tareas por estado (completadas o pendientes)
     *
     * Nombre del método: findByCompleted
     * - findBy = buscar por
     * - Completed = campo de la entidad
     *
     * Spring genera automáticamente:
     * SELECT * FROM tasks WHERE completed = ?
     */
    List<Task> findByCompleted(boolean completed);

    /**
     * Buscar tareas que contengan un texto en el título
     *
     * Nombre del método: findByTitleContainingIgnoreCase
     * - findBy = buscar por
     * - Title = campo
     * - Containing = que contenga (LIKE %texto%)
     * - IgnoreCase = sin importar mayúsculas/minúsculas
     *
     * Spring genera:
     * SELECT * FROM tasks WHERE LOWER(title) LIKE LOWER('%texto%')
     */
    List<Task> findByTitleContainingIgnoreCase(String title);

    /**
     * OTROS EJEMPLOS DE NOMBRES DE MÉTODOS (no los uses ahora, solo para que veas):
     *
     * - findByTitleAndCompleted(String title, boolean completed)
     *   → WHERE title = ? AND completed = ?
     *
     * - findByCreatedAtBefore(LocalDateTime date)
     *   → WHERE created_at < ?
     *
     * - countByCompleted(boolean completed)
     *   → COUNT(*) WHERE completed = ?
     *
     * - deleteByCompleted(boolean completed)
     *   → DELETE WHERE completed = ?
     */
}