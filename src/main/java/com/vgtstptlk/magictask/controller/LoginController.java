package com.vgtstptlk.magictask.controller;

import com.vgtstptlk.magictask.domain.User;
import com.vgtstptlk.magictask.exceptions.UserWithThisUsernameExistsException;
import com.vgtstptlk.magictask.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    UserRepository userRepository;

    @PostMapping("reg")
    ResponseEntity<?> registration(@RequestParam("user_name") String username,@RequestParam String password,
                                   @RequestParam("first_name") String firstName, @RequestParam("second_name") String secondName){
        if (userRepository.findByUsername(username).isPresent()){
            throw new UserWithThisUsernameExistsException(username);
        }
        userRepository.save(new User(username, password, firstName, secondName));
        return new ResponseEntity<>("User has successfully registered", HttpStatus.OK);
    }


    @Autowired
    LoginController(UserRepository userRepository){
        this.userRepository = userRepository;
    }
}
