package com.github.todo.domain;

/**
 * Created with IntelliJ IDEA.
 * User: Ikram
 * Date: 10/29/17
 * Time: 3:16 AM
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String task;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }


}