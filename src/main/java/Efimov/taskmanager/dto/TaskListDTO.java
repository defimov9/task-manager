package Efimov.taskmanager.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class TaskListDTO {
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;
    private String creator;
}

