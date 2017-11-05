package com.github.todo.rest;

/**
 * Created with IntelliJ IDEA.
 * User: Ikram
 * Date: 10/29/17
 * Time: 3:13 AM
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.github.todo.domain.ToDo;
import com.github.todo.service.ToDoService;

@RestController    // This class is a RestController
@RequestMapping(path="/toDoApp") // This means URL's start with /toDoApp (after Application path)
public class ToDoController {
    @Autowired
    private ToDoService toDoService;
    private Logger logger = LoggerFactory.getLogger(ToDoService.class);
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST or Update or Delete request

    /**
     * This Method will call service to create new task into ToDoList
     * @param task  String
     * @return    String
     */
    @PostMapping(path="/post")
    public @ResponseBody String createNewTask (@RequestParam String task) {
        return toDoService.createNewTask(task);
    }

    /**
     * This method will call service to update the task with provide taskId
     * @param taskId    Long
     * @param task      String
     * @return          String
     */
    @PutMapping(path="/put")
    public @ResponseBody String updateTask (@RequestParam Long taskId, @RequestParam String task) {
       return toDoService.updateTask(taskId,task);
    }

    /**
     * This Method will call the service to retrieve the Task for provided Task ID
     * @param taskId  Long
     * @return        ToDoObject
     */
    @GetMapping(path="/get")
    public @ResponseBody ToDo getTask(@RequestParam Long taskId) {
       return toDoService.getTask(taskId);
    }

    /**
     * This method will call a service to get ALL TO DO tasks
     * @return   Iterable <To DO>
     */
    @GetMapping(path="/getAll")
    public @ResponseBody Iterable<ToDo> getAllTasks() {
        return toDoService.getAllTasks();
    }
    /**
     * This method will call the service to Delete a task with provided taskID
     * @param taskId  Long
     * @return        String
     */
    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteTask(@RequestParam Long taskId) {
         return toDoService.deleteTask(taskId);
    }

}