package com.vgtstptlk.magictask.controller;


import com.vgtstptlk.magictask.domain.Changes;
import com.vgtstptlk.magictask.domain.Task;
import com.vgtstptlk.magictask.exceptions.TaskExistsException;
import com.vgtstptlk.magictask.exceptions.TaskNotFoundException;
import com.vgtstptlk.magictask.exceptions.UserNotFoundException;
import com.vgtstptlk.magictask.repos.ChangesRepository;
import com.vgtstptlk.magictask.repos.TaskRepository;
import com.vgtstptlk.magictask.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.*;

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
    private final ChangesRepository changesRepository;


    /**
     * Method adds a new task
     * @param principal security attribute
     * @param input Task
     * @return ResponseEntity
     */
    @PostMapping
    ResponseEntity<?> add(Principal principal, @ModelAttribute Task input){
        this.validateUser(principal.getName());
        this.validateTaskByDate(principal.getName(), input.getNameTask());
        return this.userRepository
                .findByUsername(principal.getName())
                .map(user -> {
                    Task result = taskRepository.save(new Task(user, input.getNameTask(), input.getDescription()));
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
    @GetMapping
    Collection<Task> readTasks(Principal principal){
        this.validateUser(principal.getName());
        return this.taskRepository.findByUserUsername(principal.getName());
    }

    /**
     * Method returns collections with completed tasks
     * @param principal security
     * @return collection with completed tasks
     */
    @GetMapping("filter/completed")
    Collection<Task> readCompletedTasks(Principal principal){
        this.validateUser(principal.getName());
        return null;
    }

    /**
     * Method returns collection with undone tasks
     * @param principal security
     * @return collection with undone tasks
     */
    @GetMapping("filter/uncompleted")
    Collection<Task> readUncompletedTasks(Principal principal){
        this.validateUser(principal.getName());
        return null;
    }

    /**
     * Searching task by name and date (default: today's date)
     * @param idTask task id
     * @return Task
     */
    @GetMapping(value = "{idTask}")
    Task readTaskByName(Principal principal, @PathVariable Long idTask){

        Collection<Changes> changes =  changesRepository
                .findByTaskUserUsernameAndTaskIdAndDateUpdate(principal.getName(), idTask, new Date());
        if (!changes.iterator().hasNext()) {
            throw new TaskNotFoundException(idTask);
        }

        return changes.iterator().next().getTask();
    }

    @GetMapping("filter/byperiod")
    List<Task> readTasksByPeriod(Principal principal, @RequestBody Date dateFrom, @RequestBody Date dateTo){
        this.validateUser(principal.getName());
        Iterator<Changes> changes = this.changesRepository
                .findByTaskUserUsernameAndDateUpdateBetween(principal.getName(), dateFrom, dateFrom).iterator();
        List<Task> tasks = new ArrayList<>();
        while (changes.hasNext()){
            tasks.add(changes.next().getTask());
        }
        return tasks;
    }

    @PutMapping(value = "{idTask}")
    ResponseEntity<?> updateTask(Principal principal, @PathVariable Long idTask, @RequestBody Task task){
        this.validateTaskByUserAndName(principal.getName(), idTask);
        //add date check
        this.taskRepository.save(task);
        return new ResponseEntity<>("Task was updated", HttpStatus.CREATED);
    }

    /**
     *
     * @param principal security
     * @param idTask id task
     * @return response entity with OK status
     */
    @DeleteMapping(value = "{idTask}")
    ResponseEntity<?> deleteTask(Principal principal, @PathVariable Long idTask){
        this.validateUser(principal.getName());
        this.validateTaskByUserAndName(principal.getName(), idTask);
        taskRepository.deleteById(idTask);
        return new ResponseEntity<>("Task was deleted", HttpStatus.OK);
    }

    private void validateUser(String userId) {
        this.userRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }

    private void validateTaskByDate(String userId, String nameTask){
        Optional<Task> taskNewOptional = this.taskRepository.findByUserUsernameAndNameTask(userId, nameTask);
        if (taskNewOptional.isPresent()){
            if (changesRepository.findByTaskUserUsernameAndDateUpdateAndDescription(userId, new Date(), "Created")
                    .isPresent()){
                throw new TaskExistsException();
            }

        }

    }

    private void validateTaskByUserAndName(String userId, Long id){
        this.taskRepository.findByUserUsernameAndId(userId, id);
    }

    @Autowired
    public MagicTaskController(TaskRepository taskRepository, UserRepository userRepository,
                               ChangesRepository changesRepository) {
        this.changesRepository = changesRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
}
