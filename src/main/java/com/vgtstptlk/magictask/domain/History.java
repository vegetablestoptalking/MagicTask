package com.vgtstptlk.magictask.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Task task;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public History() {
    }

    public History(String description) {
        this.description = description;
    }
}

