package Efimov.taskmanager.repository;

import Efimov.taskmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import Efimov.taskmanager.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
}

