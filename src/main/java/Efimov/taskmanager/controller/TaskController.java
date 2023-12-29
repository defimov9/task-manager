package Efimov.taskmanager.controller;

import Efimov.taskmanager.entity.Task;
import Efimov.taskmanager.entity.User;
import Efimov.taskmanager.repository.TaskListRepository;
import Efimov.taskmanager.repository.TaskRepository;
import Efimov.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private UserRepository userRepository;

    // Просмотр всех задач
    @GetMapping
    public String listTasks(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(currentUserName);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            List<Task> tasks = taskRepository.findByUser(currentUser);
            model.addAttribute("tasks", tasks);
        }

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
    public String saveTask(@ModelAttribute Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tasks/new";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(currentUserName);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            task.setUser(currentUser);
            taskRepository.save(task);
        }

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
    public String updateTask(@PathVariable Long id, @ModelAttribute Task task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tasks/edit";
        }

        // Получение текущего пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(currentUserName);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();

            // Получение существующей задачи из базы данных для обновления
            Optional<Task> existingTaskOptional = taskRepository.findById(id);
            if (existingTaskOptional.isPresent()) {
                Task existingTask = existingTaskOptional.get();

                // Обновление полей существующей задачи
                existingTask.setTitle(task.getTitle());
                existingTask.setDescription(task.getDescription());

                // Присвоение пользователя задаче
                existingTask.setUser(currentUser);

                // Сохранение обновленной задачи
                taskRepository.save(existingTask);
            }
        }

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
