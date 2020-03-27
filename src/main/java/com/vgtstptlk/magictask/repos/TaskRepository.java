package com.vgtstptlk.magictask.repos;

import com.vgtstptlk.magictask.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Collection<Task> findByUserUsername(String username);
    Collection<Task> findByUserUsernameAndNameTaskAndDateCreation(String username, String nameTask, Date dateCreation);
    Optional<Task> findByNameTaskAndAndDateCreation(String nameTask, Date dateCreation);
}
