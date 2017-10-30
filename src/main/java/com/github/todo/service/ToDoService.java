package com.github.todo.service;

import com.github.todo.domain.ToDo;
import com.github.todo.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: Ikram
 * Date: 10/29/17
 * Time: 9:55 PM
 * To change this template use File | Settings | File Templates.
 */

@Service
public class ToDoService {
    @Autowired
    private ToDoRepository toDoRepository;
    private Logger logger = LoggerFactory.getLogger(ToDoService.class);

    /**
     * This Method will create new task in TO DO
     * @param task  String
     * @return  String
     */
    public String createNewTask (String task) {
        logger.info("Creating New Task ... !");
        ToDo toDoElement = new ToDo();
        toDoElement.setTask(task);
        toDoRepository.save(toDoElement);
        logger.info("Saved new Task in Entity ToDo!");
        return "Saved";
    }

    /**
     * This method will Update the task for Provided taskID
     * @param taskId
     * @param task
     * @return   String
     */
    public String updateTask (Long taskId, String task) {
        String responseString = "";
        if (toDoRepository.exists(taskId)) {
            logger.info("Updating Task ... !");
            ToDo toDoElement = toDoRepository.findOne(taskId);
            toDoElement.setTask(task);
            toDoRepository.save(toDoElement);
            logger.info("Updated Task with Id " + taskId + " in Entity ToDo!");
            responseString = "Updated!";
        }
        else {
            logger.warn("No Task with Id " + taskId + " exists in Entity ToDo!");
            responseString = "No Task with Given ID exist!";
        }
        return responseString;
    }

    /**
     *  This method will retrieve a task from TODOList with provided taskId
     * @param taskId
     * @return  ToDoObject
     */
    public ToDo getTask(Long taskId) {
            ToDo toDoInstance = null;
        if (toDoRepository.exists(taskId)){
            logger.info("Retrieving Task ... !");
            toDoInstance = toDoRepository.findOne(taskId);
            logger.info("Retrieved Task with Id " + taskId + " from Entity ToDo!");
        }
        else  {
            logger.warn("Task with ID " + taskId + " does not exist in Entity ToDo!");
        }
        return toDoInstance;
    }

    /**
     * This method will delete the task for provided taskId
     * @param taskId
     * @return String
     */
    public String deleteTask(Long taskId) {
        String responseString = "";
        if  (toDoRepository.exists(taskId)){
            logger.info("Deleting Task ... !");
             toDoRepository.delete(taskId);
             logger.info("Deleted Task with Id " + taskId + " from Entity ToDo!");
             responseString = "Deleted!";
        }
        else if (taskId == null) {
             responseString = deleteAllTask();
        }
        else {
             logger.warn("No Task with Id " + taskId + " exists in Entity ToDo!");
             responseString = "Task with Given Id not Found!";
        }
        return responseString;
    }

    /**
     * This method will retrieve all the tasks from TODOList
     * @return  Iterable TODOObject
     */
    public Iterable<ToDo> getAllTasks() {
        Iterable<ToDo> iterateInstance = null;
        if  (toDoRepository.count() > 0) {
            iterateInstance = toDoRepository.findAll();
            logger.info("Retrieved all Tasks from Entity ToDo!");
        }
        else {
            logger.warn("No task exists in Entity ToDo!");
        }
        return  iterateInstance;
    }

    /**
     * This Method will delete all tasks for TODOList
     * @return
     */
    public String deleteAllTask() {
        String responseString ="";
        if (toDoRepository.count() > 0){
            logger.info("Deleting All Tasks ... ");
            toDoRepository.deleteAll();
            logger.info("Deleted All Tasks from Entity ToDo!");
            responseString = "Deleted All Tasks!";
        }
        else {
            logger.warn("No Task exists in Entity ToDo!");
            responseString =  "No Task to be Deleted!";
        }
        return responseString;
    }
}
