package Efimov.taskmanager.controller;

import Efimov.taskmanager.entity.Task;
import Efimov.taskmanager.entity.TaskList;
import Efimov.taskmanager.entity.User;
import Efimov.taskmanager.repository.TaskListRepository;
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
@RequestMapping("/tasklists")
public class TaskListController {

    @Autowired
    private TaskListRepository taskListRepository;

    @Autowired
    private UserRepository userRepository;

    // Просмотр всех списков задач
    @GetMapping
    public String listTaskLists(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(currentUserName);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            List<TaskList> taskLists = taskListRepository.findByUser(currentUser);
            model.addAttribute("taskLists", taskLists);
        }

        return "tasklists/list";
    }

    // Форма для добавления нового списка задач
    @GetMapping("/new")
    public String newTaskListForm(Model model) {
        model.addAttribute("taskList", new TaskList());
        return "tasklists/new";
    }

    // Сохранение нового списка задач
    @PostMapping
    public String saveTaskList(@ModelAttribute TaskList taskList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tasklists/new";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(currentUserName);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();
            taskList.setUser(currentUser); // Привязываем список задач к пользователю
            taskListRepository.save(taskList);
        }

        return "redirect:/tasklists";
    }

    // Форма для редактирования списка задач
    @GetMapping("/edit/{id}")
    public String editTaskListForm(@PathVariable Long id, Model model) {
        model.addAttribute("taskList", taskListRepository.findById(id).orElse(null));
        return "tasklists/edit";
    }

    // Обновление списка задач
    @PostMapping("/update/{id}")
    public String updateTaskList(@PathVariable Long id, @ModelAttribute TaskList taskList, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "tasklists/edit";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();

        Optional<User> userOptional = userRepository.findByUsername(currentUserName);
        if (userOptional.isPresent()) {
            User currentUser = userOptional.get();

            // Получение существующего списка задач из базы данных для обновления
            Optional<TaskList> existingTaskListOptional = taskListRepository.findById(id);
            if (existingTaskListOptional.isPresent()) {
                TaskList existingTaskList = existingTaskListOptional.get();

                // Обновление данных списка задач
                existingTaskList.setTitle(taskList.getTitle());
                existingTaskList.setDescription(taskList.getDescription());

                taskListRepository.save(existingTaskList);
            }
        }

        return "redirect:/tasklists";
    }

    // Удаление списка задач
    @GetMapping("/delete/{id}")
    public String deleteTaskList(@PathVariable Long id) {
        taskListRepository.deleteById(id);
        return "redirect:/tasklists";
    }

    @GetMapping("/tasklist/{taskListId}/tasks")
    public String listTasksInTaskList(@PathVariable Long taskListId, Model model) {
        List<Task> tasks = taskListRepository.findTasksByTaskListId(taskListId);
        model.addAttribute("tasks", tasks);
        return "tasklist-tasks"; // Название шаблона View для отображения задач в списке
    }
}

