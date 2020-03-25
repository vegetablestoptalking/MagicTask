package com.vgtstptlk.magictask.controller;


import com.vgtstptlk.magictask.domain.Task;
import com.vgtstptlk.magictask.exceptions.TaskExistsException;
import com.vgtstptlk.magictask.exceptions.UserNotFoundException;
import com.vgtstptlk.magictask.repos.TaskRepository;
import com.vgtstptlk.magictask.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping("/{userId}/tasks")
public class MagicTaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Task input){
        this.validateUser(userId);
        this.validateTaskByDate(userId, input.nameTask);
        return this.userRepository
                .findByUsername(userId)
                .map(user -> {
                    Task result = taskRepository.save(new Task(user, input.nameTask, input.description));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Task> readTasks(@PathVariable String userId){
        this.validateUser(userId);
        return this.taskRepository.findByUserUsername(userId);
    }

    private void validateUser(String userId) {
        this.userRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }

    private void validateTaskByDate(String userId, String nameTask){
        if (taskRepository.findByUserUsernameAndNameTaskAndDateCreation(userId, nameTask, new Date()).size() > 0){
            throw new TaskExistsException();
        }
    }

    @Autowired
    public MagicTaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

}
