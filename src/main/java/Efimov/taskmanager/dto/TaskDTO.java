package Efimov.taskmanager.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class TaskDTO {
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    private String title;

    private String description;

    @NotNull(message = "Completed status must be specified")
    private Boolean completed;
}

