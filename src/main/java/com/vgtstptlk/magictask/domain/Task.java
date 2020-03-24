package com.vgtstptlk.magictask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Task {
    @JsonIgnore
    @ManyToOne
    private User user;

    @Id
    @GeneratedValue
    private Long id;

    public boolean flag;
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateCreation;
    @Temporal(TemporalType.TIMESTAMP)
    public Date dateCompletion;
    public String description;

    public Task() {
    }

    public Task(String description) {
        this.description = description;
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
}
