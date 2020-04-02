package com.vgtstptlk.magictask.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Changes {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Task task;

    @Temporal(TemporalType.DATE)
    private Date dateUpdate;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date date) {
        this.dateUpdate = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Changes() {
    }

    public Changes(Task task, String description) {
        this.description = description;
        this.dateUpdate = new Date();
        this.task = task;
    }
}

