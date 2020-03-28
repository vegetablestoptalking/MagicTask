package com.vgtstptlk.magictask.controller;


import com.vgtstptlk.magictask.domain.Task;
import com.vgtstptlk.magictask.exceptions.TaskExistsException;
import com.vgtstptlk.magictask.exceptions.TaskNotFoundException;
import com.vgtstptlk.magictask.exceptions.UserNotFoundException;
import com.vgtstptlk.magictask.repos.TaskRepository;
import com.vgtstptlk.magictask.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

/**
 * Task controller
 * @author Magerram Zeynalov
 * @version 1.00
 */
@RestController
@RequestMapping("/api/tasks")
public class MagicTaskController {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    /**
     * Method adds a new task
     * @param principal security attribute
     * @param input Task
     * @return ResponseEntity
     */
    @RequestMapping(name = "add", method = RequestMethod.POST)
    ResponseEntity<?> add(Principal principal, @RequestBody Task input){
        this.validateUser(principal.getName());
        this.validateTaskByDate(principal.getName(), input.nameTask);
        return this.userRepository
                .findByUsername(principal.getName())
                .map(user -> {
                    Task result = taskRepository.save(new Task(user, input.nameTask, input.description));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();
    }

    /**
     * Method returns all your tasks
     *
     * */
    @RequestMapping(name = "readAllTasks", method = RequestMethod.GET)
    Collection<Task> readTasks(Principal principal){
        this.validateUser(principal.getName());
        return this.taskRepository.findByUserUsername(principal.getName());
    }

    /**
     * Searching task by name and date (default: today's date)
     * @param nameTask task name
     * @param textDate date the task was created. Default today's date
     * @return Task
     */
    @RequestMapping(name = "filter", method = RequestMethod.GET)
    Task readTaskByName(Principal principal, @RequestBody String nameTask, @RequestBody String textDate){
        Date date = (!textDate.isEmpty()) ? new Date(textDate) : new Date();
        return this.taskRepository.findByNameTaskAndAndDateCreation(nameTask, date).orElseThrow(
                ()-> new TaskNotFoundException(nameTask)
        );
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
