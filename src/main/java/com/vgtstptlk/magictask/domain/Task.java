package com.vgtstptlk.magictask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

@Entity
public class Task {
    @JsonIgnore
    @ManyToOne
    private User user;

    @Id
    @GeneratedValue
    private Long id;

    public String nameTask;
    public boolean flag;
    @Column(updatable = false)
    @Temporal(TemporalType.DATE)
    public Date dateCreation;
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateCompletion;
    public String description;

    public Task() {
    }

    public Task(User user, String nameTask, String description) {
        this.user = user;
        this.description = description;
        this.nameTask = nameTask;
        this.dateCreation = new Date();
    }

    public boolean isFlag() {
        return flag;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateCompletion() {
        return dateCompletion;
    }

    public String getDescription() {
        return description;
    }

    public Long getId(){
        return id;
    }
}
