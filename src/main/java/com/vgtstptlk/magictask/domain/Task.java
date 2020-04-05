package com.vgtstptlk.magictask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import javax.swing.text.View;
import java.util.*;

@Entity
public class Task {
    @Id
    @GeneratedValue
    @JsonView(Views.ShortTask.class)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonView(Views.ShortTask.class)
    private String nameTask;

    @JsonView(Views.ShortTask.class)
    private String description;

    @OneToMany(cascade = {CascadeType.ALL})
    @JsonView(Views.FullTask.class)
    public List<Changes> changes = new ArrayList<>();

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

    public List<Changes> getChanges() {
        return changes;
    }

    public void setChanges(List<Changes> changes) {
        this.changes = changes;
    }


}
