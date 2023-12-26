package Efimov.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Efimov.taskmanager.entity.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}
