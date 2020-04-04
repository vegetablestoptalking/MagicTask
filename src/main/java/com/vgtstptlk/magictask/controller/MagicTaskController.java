package com.vgtstptlk.magictask.controller;


import com.vgtstptlk.magictask.domain.Changes;
import com.vgtstptlk.magictask.domain.Task;
import com.vgtstptlk.magictask.exceptions.TaskCantBeUpdatedException;
import com.vgtstptlk.magictask.exceptions.TaskExistsException;
import com.vgtstptlk.magictask.exceptions.TaskNotFoundException;
import com.vgtstptlk.magictask.exceptions.UserNotFoundException;
import com.vgtstptlk.magictask.repos.ChangesRepository;
import com.vgtstptlk.magictask.repos.TaskRepository;
import com.vgtstptlk.magictask.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
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
     *
     * @param principal
     * @param nameTask
     * @param description
     * @return response entity
     */
    @PostMapping
    ResponseEntity<?> add(Principal principal, @RequestParam String nameTask, @RequestParam String description){
        this.validateUser(principal.getName());
        this.validateTaskByDate(principal.getName(), nameTask);
        return this.userRepository
                .findByUsername(principal.getName())
                .map(user -> {
                    Task result = taskRepository.save(new Task(user, nameTask, description));
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(result.getId()).toUri());
                    return new ResponseEntity<>("Task added", httpHeaders, HttpStatus.CREATED);
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
        Collection<Task> tasks = this.taskRepository.findByUserUsername(principal.getName());
        Iterator<Task> taskIterator = tasks.iterator();
        Collection<Task> completedTask = new ArrayList<>();
        while (taskIterator.hasNext()){
            Task tempTask = taskIterator.next();
            List<Changes> tempChanges = tempTask.getChanges();
            if (tempChanges.get(tempChanges.size()-1).getDescription().equals("Completed")){
                completedTask.add(tempTask);
            }
        }
        return completedTask;
    }

    /**
     * Method returns collection with undone tasks
     * @param principal security
     * @return collection with undone tasks
     */
    @GetMapping("filter/uncompleted")
    Collection<Task> readUncompletedTasks(Principal principal){
        this.validateUser(principal.getName());
        Collection<Task> tasks = this.taskRepository.findByUserUsername(principal.getName());
        Iterator<Task> taskIterator = tasks.iterator();
        Collection<Task> uncompletedTask = new ArrayList<>();
        while (taskIterator.hasNext()){
            Task tempTask = taskIterator.next();
            List<Changes> tempChanges = tempTask.getChanges();
            if (!tempChanges.get(tempChanges.size()-1).getDescription().equals("Completed")){
                uncompletedTask.add(tempTask);
            }
        }
        return uncompletedTask;
    }

    /**
     * Searching task by name and date (default: today's date)
     * @param idTask id
     * @return Task
     */
    @GetMapping(value = "{idTask}")
    Task readTaskById(Principal principal, @PathVariable Long idTask){

        Optional<Changes> changes =  changesRepository
                .findByTaskUserUsernameAndTaskIdAndDateUpdate(principal.getName(), idTask, new Date());
        changes.orElseThrow(
                () -> new TaskNotFoundException(idTask)
        );

        return changes.get().getTask();
    }

    /**
     *
     * @param principal security
     * @param dateFrom date from period
     * @param dateTo date to period
     * @return list
     */
    @GetMapping("filter/byperiod")
    List<Task> readTasksByPeriod(Principal principal,
                                 @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                 @NotNull @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateTo){
        this.validateUser(principal.getName());
        Iterator<Changes> changes = this.changesRepository
                .findByTaskUserUsernameAndDateUpdateBetween(principal.getName(), dateFrom, dateTo).iterator();
        List<Task> tasks = new ArrayList<>();
        while (changes.hasNext()){
            tasks.add(changes.next().getTask());
        }
        return tasks;
    }

    /**
     *
     * @param principal security
     * @param idTask id
     * @param nameTask name_task
     * @param description description
     * @param date date
     * @return response entity
     */
    @PutMapping(value = "{idTask}")
    ResponseEntity<?> updateTask(Principal principal, @PathVariable Long idTask, @RequestParam String nameTask,
                                 @RequestParam String description,
                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        this.validateTaskByUserAndName(principal.getName(), idTask);
        if (date == null){
            date = new Date();
        }
        this.validateByUpdate(principal.getName(), idTask, nameTask, date);
        Optional<Changes> changes = this.changesRepository
                .findByTaskUserUsernameAndTaskIdAndDateUpdate(principal.getName(), idTask, date);
        changes.orElseThrow(
                () -> new TaskNotFoundException(idTask)
        );

        Changes tempChanges = changes.get();
        Task task = tempChanges.getTask();
        if (task.getChanges().get(task.getChanges().size()-1).getDescription().equals("Completed")){
            throw new TaskCantBeUpdatedException(idTask);
        }
        task.setNameTask(nameTask);
        task.setDescription(description);
        List<Changes> listChanges = tempChanges.getTask().getChanges();
        listChanges.add(new Changes(task, "Updated"));
        task.setChanges(listChanges);
        this.taskRepository.save(task);
        return new ResponseEntity<>("Task was updated", HttpStatus.OK);
    }

    @PutMapping("completed/{idTask}")
    ResponseEntity<?> completeTask(Principal principal, @PathVariable Long idTask){
        this.validateUser(principal.getName());
        Task task = this.taskRepository.findByUserUsernameAndId(principal.getName(), idTask).orElseThrow(
                () -> new TaskNotFoundException(idTask)
        );
        List<Changes> tempChanges = task.getChanges();
        tempChanges.add(new Changes(task, "Completed"));
        task.setChanges(tempChanges);
        this.taskRepository.save(task);
        return new ResponseEntity<>("Task was completed", HttpStatus.OK);
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
            if (changesRepository.findByTaskUserUsernameAndTaskIdAndDateUpdateAndDescription(userId,
                    taskNewOptional.get().getId(), new Date(), "Created")
                    .isPresent()){
                throw new TaskExistsException();
            }

        }

    }

    private void validateTaskByUserAndName(String userId, Long id){
        this.taskRepository.findByUserUsernameAndId(userId, id).orElseThrow(
                () -> new TaskNotFoundException(id)
        );
    }

    private void validateByUpdate(String username, Long idTask, String nameTask, Date date){
        Optional<Changes> changes = this.changesRepository.findByTaskUserUsernameAndTaskIdAndDateUpdate(username, idTask, date);
        if (changes.isPresent() && (!changes.get().getTask().getUser().getId().equals(idTask))){
            throw new TaskExistsException();
        }
    }

    @Autowired
    public MagicTaskController(TaskRepository taskRepository, UserRepository userRepository,
                               ChangesRepository changesRepository) {
        this.changesRepository = changesRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }
}
