package Efimov.taskmanager.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserDTO {
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}
