package Efimov.taskmanager.repository;

import Efimov.taskmanager.entity.Task;
import Efimov.taskmanager.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import Efimov.taskmanager.entity.TaskList;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    @Query("SELECT t FROM TaskList tl JOIN tl.tasks t WHERE tl.id = ?1")
    List<Task> findTasksByTaskListId(Long taskListId);

    Page<TaskList> findByUser(User user, Pageable pageable);
}
