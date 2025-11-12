package com.example.todoappwithspring.controller;

import com.example.todoappwithspring.model.Task;
import com.example.todoappwithspring.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CONTROLLER: Maneja las peticiones HTTP (REST API)
 *
 * ¿Qué es REST?
 * Es una arquitectura para crear APIs web usando HTTP
 *
 * Métodos HTTP:
 * - GET    → Obtener datos
 * - POST   → Crear datos
 * - PUT    → Actualizar datos completos
 * - PATCH  → Actualizar datos parciales
 * - DELETE → Eliminar datos
 */
@RestController  // @RestController = @Controller + @ResponseBody (retorna JSON automáticamente)
@RequestMapping("/api/tasks")  // Todas las rutas empiezan con /api/tasks
@CrossOrigin(origins = "*")    // Permite peticiones desde cualquier origen (para desarrollo)
public class TaskController {

    /**
     * INYECCIÓN DEL SERVICE
     * El Controller NO accede directamente al Repository
     * Siempre pasa por el Service (buena práctica)
     */
    @Autowired
    private TaskService taskService;

    /**
     * ENDPOINT 1: OBTENER TODAS LAS TAREAS
     *
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks
     *
     * ResponseEntity<List<Task>>:
     * - Permite controlar el código de respuesta HTTP (200, 404, 500, etc.)
     * - Contiene la lista de tareas en formato JSON
     */
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);  // HTTP 200 OK
    }

    /**
     * ENDPOINT 2: OBTENER UNA TAREA POR ID
     *
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks/1
     *
     * @PathVariable Long id:
     * - Captura el ID de la URL
     * - Ejemplo: /api/tasks/5 → id = 5
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)           // Si existe: HTTP 200 OK
                .orElse(ResponseEntity.notFound().build());  // Si no existe: HTTP 404 NOT FOUND
    }

    /**
     * ENDPOINT 3: CREAR UNA NUEVA TAREA
     *
     * Método HTTP: POST
     * URL: http://localhost:8080/api/tasks
     * Body (JSON):
     * {
     *   "title": "Aprender Spring Boot",
     *   "description": "Crear mi primera API REST"
     * }
     *
     * @RequestBody Task task:
     * - Convierte el JSON recibido en un objeto Task automáticamente
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task newTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTask);  // HTTP 201 CREATED
    }

    /**
     * ENDPOINT 4: ACTUALIZAR UNA TAREA
     *
     * Método HTTP: PUT
     * URL: http://localhost:8080/api/tasks/1
     * Body (JSON):
     * {
     *   "title": "Título actualizado",
     *   "description": "Descripción actualizada",
     *   "completed": true
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task) {
        try {
            Task updatedTask = taskService.updateTask(id, task);
            return ResponseEntity.ok(updatedTask);  // HTTP 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // HTTP 404 NOT FOUND
        }
    }

    /**
     * ENDPOINT 5: ELIMINAR UNA TAREA
     *
     * Método HTTP: DELETE
     * URL: http://localhost:8080/api/tasks/1
     *
     * ResponseEntity<Void>:
     * - No retorna contenido, solo el código de estado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();  // HTTP 204 NO CONTENT
    }

    /**
     * ENDPOINT 6: CAMBIAR ESTADO DE UNA TAREA (completada/pendiente)
     *
     * Método HTTP: PATCH
     * URL: http://localhost:8080/api/tasks/1/toggle
     *
     * PATCH se usa para actualizaciones parciales
     */
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Task> toggleTaskStatus(@PathVariable Long id) {
        try {
            Task task = taskService.toggleTaskStatus(id);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * ENDPOINT 7: OBTENER TAREAS POR ESTADO
     *
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks/status/true  (completadas)
     * URL: http://localhost:8080/api/tasks/status/false (pendientes)
     */
    @GetMapping("/status/{completed}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable boolean completed) {
        List<Task> tasks = taskService.getTasksByStatus(completed);
        return ResponseEntity.ok(tasks);
    }

    /**
     * ENDPOINT 8: BUSCAR TAREAS POR TÍTULO
     *
     * Método HTTP: GET
     * URL: http://localhost:8080/api/tasks/search?title=spring
     *
     * @RequestParam String title:
     * - Captura parámetros de la query string (?title=...)
     */
    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String title) {
        List<Task> tasks = taskService.searchTasksByTitle(title);
        return ResponseEntity.ok(tasks);
    }
}