package Efimov.taskmanager.controller; // Измените на свой пакет

import Efimov.taskmanager.dto.UserDTO; // Измените на ваш DTO
import Efimov.taskmanager.service.UserService; // Используйте ваш UserService
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.Valid;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @GetMapping
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping
    public String register(@Valid UserDTO userDto, BindingResult result, Model model) {
        log.info("Register request {}", userDto);
        if (!result.hasErrors()) {
            var user = userDto.toEntity(); // Предполагается, что у вас есть метод toEntity
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userService.save(user);
            return "redirect:/login";
        } else {
            log.info("Has errors: {}", result.getFieldErrors()
                    .stream()
                    .map(FieldError::getField)
                    .collect(Collectors.toList()));
            return "register";
        }
    }
}
