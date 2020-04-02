package com.vgtstptlk.magictask.repos;

import com.vgtstptlk.magictask.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Task> findByUserUsername(String username);
    Optional<Task> findByUserUsernameAndId(String username, Long id);
    Optional<Task> findByUserUsernameAndNameTask(String username, String nameTask);
}
