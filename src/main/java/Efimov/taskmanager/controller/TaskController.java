package Efimov.taskmanager.controller;

import Efimov.taskmanager.dto.TaskDTO;
import Efimov.taskmanager.entity.Task;
import Efimov.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    // Просмотр всех задач
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskRepository.findAll());
        return "tasks/list";
    }

    // Форма для добавления новой задачи
    @GetMapping("/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "tasks/new";
    }

    // Сохранение новой задачи
    @PostMapping
    public String saveTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    // Форма для редактирования задачи
    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).orElse(null));
        return "tasks/edit";
    }

    // Обновление задачи
    @PostMapping("/update/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task) {
        task.setId(id);
        taskRepository.save(task);
        return "redirect:/tasks";
    }

    // Удаление задачи
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
        return "redirect:/tasks";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateTaskStatus(@PathVariable Long id, @RequestParam(value = "status", required = false) boolean status) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task != null) {
            task.setStatus(status);
            taskRepository.save(task);
        }
        return "redirect:/tasks";
    }
}
