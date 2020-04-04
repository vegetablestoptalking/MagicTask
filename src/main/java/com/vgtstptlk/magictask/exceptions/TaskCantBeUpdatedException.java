package com.vgtstptlk.magictask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskCantBeUpdatedException extends RuntimeException{
    public TaskCantBeUpdatedException(Long id){
        super("Task with id '"+id+"' completed. You can't change it.");
    }
}
