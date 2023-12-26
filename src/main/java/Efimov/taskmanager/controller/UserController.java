package Efimov.taskmanager.controller;

import Efimov.taskmanager.entity.User;
import Efimov.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Просмотр всех пользователей
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users/list";
    }

    // Форма для добавления нового пользователя
    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    // Сохранение нового пользователя
    @PostMapping
    public String saveUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    // Форма для редактирования пользователя
    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElse(null));
        return "users/edit";
    }

    // Обновление пользователя
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        user.setId(id); // Убедитесь, что у вашего класса User есть метод setId
        userRepository.save(user);
        return "redirect:/users";
    }

    // Удаление пользователя
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/users";
    }
}
