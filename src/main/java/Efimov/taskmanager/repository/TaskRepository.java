package Efimov.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Efimov.taskmanager.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

