package com.vgtstptlk.magictask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "user")
    private Set<Task> task = new HashSet<>();


    public String firstName;
    public String secondName;

    @JsonIgnore
    @NotNull
    public String username;

    @JsonIgnore
    @NotNull
    public String password;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = (new BCryptPasswordEncoder()).encode(password);
    }

    public User() {
    }

    public User(String username, String password, String firstName, String secondName){
        this.username = username;
        this.password = (new BCryptPasswordEncoder()).encode(password);
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
