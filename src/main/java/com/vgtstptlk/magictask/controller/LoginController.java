package com.vgtstptlk.magictask.controller;

import com.vgtstptlk.magictask.domain.User;
import com.vgtstptlk.magictask.exceptions.UserWithThisUsernameExistsException;
import com.vgtstptlk.magictask.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    UserRepository userRepository;

    @PostMapping("reg")
    ResponseEntity<?> registration(@ModelAttribute User input){
        if (userRepository.findByUsername(input.username).isPresent()){
            throw new UserWithThisUsernameExistsException(input.username);
        }
        userRepository.save(new User(input.username, input.password, input.firstName, input.secondName));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Autowired
    LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
