package Efimov.taskmanager.controller;

import Efimov.taskmanager.entity.TaskList;
import Efimov.taskmanager.repository.TaskListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tasklists")
public class TaskListController {

    @Autowired
    private TaskListRepository taskListRepository;

    // Просмотр всех списков задач
    @GetMapping
    public String listTaskLists(Model model) {
        model.addAttribute("taskLists", taskListRepository.findAll());
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
    public String saveTaskList(@ModelAttribute TaskList taskList) {
        taskListRepository.save(taskList);
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
    public String updateTaskList(@PathVariable Long id, @ModelAttribute TaskList taskList) {
        taskList.setId(id); // Убедитесь, что у вашего класса TaskList есть метод setId
        taskListRepository.save(taskList);
        return "redirect:/tasklists";
    }

    // Удаление списка задач
    @GetMapping("/delete/{id}")
    public String deleteTaskList(@PathVariable Long id) {
        taskListRepository.deleteById(id);
        return "redirect:/tasklists";
    }
}

