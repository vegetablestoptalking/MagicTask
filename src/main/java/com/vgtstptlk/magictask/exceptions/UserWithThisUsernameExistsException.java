package com.vgtstptlk.magictask.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserWithThisUsernameExistsException extends RuntimeException {
    public UserWithThisUsernameExistsException(String username){
        super("User with username '" + username + "' already exists");
    }
}
