/* Copyright 2015, Chen Su */
package com.chensuworks.amazingtaskbox.models;

import java.util.Date;

public class Task {

    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private TaskState state;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;

        Date currentDate = new Date();
        this.createdDate = currentDate;
        this.modifiedDate = currentDate;

        this.state = TaskState.TODO;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setdescription (String description) {
        this.description = description;
    }

    public void setCreatedDate(Date date) {
        this.createdDate = date;
    }

    public void setModifiedDate(Date date) {
        this.modifiedDate = date;
    }

    public void setState(TaskState state) {
        this.state = state;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public TaskState getState() {
        return this.state;
    }

}
