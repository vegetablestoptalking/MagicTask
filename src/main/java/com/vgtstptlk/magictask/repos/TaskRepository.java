package com.vgtstptlk.magictask.repos;

import com.vgtstptlk.magictask.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Collection<Task> findByUserUsername(String username);
}