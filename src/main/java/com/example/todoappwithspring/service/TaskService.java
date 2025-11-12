package com.example.todoappwithspring.service;

import com.example.todoappwithspring.model.Task;
import com.example.todoappwithspring.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVICE: Contiene la LÓGICA DE NEGOCIO
 *
 * ¿Por qué usar un Service?
 * - Separa la lógica de negocio del controlador
 * - Reutilizable en diferentes partes de la app
 * - Más fácil de testear
 * - Principio de responsabilidad única
 */
@Service
public class TaskService {

    /**
     * INYECCIÓN DE DEPENDENCIAS
     *
     * @Autowired le dice a Spring:
     * "Por favor, crea automáticamente una instancia de TaskRepository
     *  y asígnala a esta variable"
     *
     * No necesitas hacer: taskRepository = new TaskRepository()
     * Spring lo hace por ti (Inversión de Control)
     */
    @Autowired
    private TaskRepository taskRepository;

    /**
     * OBTENER TODAS LAS TAREAS
     *
     * Usa el método findAll() del Repository
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * OBTENER UNA TAREA POR ID
     *
     * Retorna Optional<Task> porque la tarea puede NO existir
     * Optional es una forma segura de manejar valores que pueden ser null
     */
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    /**
     * CREAR UNA NUEVA TAREA
     *
     * El método save() guarda la tarea en la BD
     * Si no tiene ID, la crea (INSERT)
     * Si tiene ID, la actualiza (UPDATE)
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * ACTUALIZAR UNA TAREA EXISTENTE
     *
     * Pasos:
     * 1. Buscar la tarea por ID
     * 2. Si existe, actualizar sus campos
     * 3. Guardar los cambios
     * 4. Si no existe, lanzar una excepción
     */
    public Task updateTask(Long id, Task taskDetails) {
        // Buscar la tarea
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        // Actualizar los campos
        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setCompleted(taskDetails.isCompleted());

        // Guardar y retornar
        return taskRepository.save(task);
    }

    /**
     * ELIMINAR UNA TAREA
     *
     * Elimina la tarea de la base de datos
     */
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    /**
     * OBTENER TAREAS POR ESTADO
     *
     * true = tareas completadas
     * false = tareas pendientes
     */
    public List<Task> getTasksByStatus(boolean completed) {
        return taskRepository.findByCompleted(completed);
    }

    /**
     * CAMBIAR ESTADO DE UNA TAREA
     *
     * Alterna entre completada y pendiente
     * Si estaba completada → pendiente
     * Si estaba pendiente → completada
     */
    public Task toggleTaskStatus(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada con id: " + id));

        // Alternar el estado
        task.setCompleted(!task.isCompleted());

        return taskRepository.save(task);
    }

    /**
     * BUSCAR TAREAS POR TÍTULO
     *
     * Busca tareas que contengan el texto en el título
     */
    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }
}