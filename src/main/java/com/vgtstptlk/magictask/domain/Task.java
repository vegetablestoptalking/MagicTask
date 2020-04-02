package com.vgtstptlk.magictask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User user;

    private String nameTask;

    private String description;

    @OneToMany(cascade = {CascadeType.ALL})
    public Set<Changes> changes = new HashSet<>();

    public String getNameTask() {
        return nameTask;
    }

    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task(User user, String nameTask, String description) {
        this.user = user;
        this.description = description;
        this.nameTask = nameTask;
        this.changes.add(new Changes(this, "Created"));
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Changes> getChanges() {
        return changes;
    }

    public void setChanges(Set<Changes> changes) {
        this.changes = changes;
    }
}