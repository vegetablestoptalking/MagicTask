package com.vgtstptlk.magictask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TaskExistsException extends RuntimeException {
    public TaskExistsException(){
        super("A task with this name exists on this day");
    }
}
