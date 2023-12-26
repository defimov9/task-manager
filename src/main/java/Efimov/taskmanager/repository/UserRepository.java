package Efimov.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Efimov.taskmanager.entity.User; // Замените на свой пакет сущности

public interface UserRepository extends JpaRepository<User, Long> {
}
