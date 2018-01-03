package com.github.todo.service;

import com.github.todo.domain.ToDo;
import com.github.todo.repository.ToDoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ikram
 */

@Service
public class ToDoService {
    private ToDoRepository toDoRepository;
    private Logger logger = LoggerFactory.getLogger(ToDoService.class);


    @Autowired
    public  ToDoService (ToDoRepository toDoRepository){
        this.toDoRepository = toDoRepository;
    }

    /**
     * This Method will create new toDo in TODOList
     * @param toDo  ToDo
     * @return  ToDo
     */
    public ToDo createToDo (ToDo toDo) {
        logger.info("Creating New Task ... !");
        ToDo toDoInstance = toDoRepository.save(toDo);
        logger.info("Saved new Task in Entity ToDo!");
        return toDoInstance;
    }

    /**
     * This method will Update the todo for Provided ID
     * @param id
     * @param toDo ToDo
     * @return   ResponseEntity<ToDo>
     */

    public ResponseEntity<ToDo> updateToDo (String id, ToDo toDo) {
        ToDo toDoData = toDoRepository.findOne(id);
        if(toDoData == null) {
            logger.warn("No Task with Id " + id + " exists in Entity ToDo!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            logger.info("Updating Task ... !");
             toDoData = toDoRepository.save(toDo);
            logger.info("Updated Task with Id " + id + " in Entity ToDo!");
            return new ResponseEntity<>(toDoData, HttpStatus.OK);
        }
    }

    /**
     *  This method will retrieve a todo from TODOList with provided taskId
     * @param toDoId
     * @return  ToDoObject
     */

    public ResponseEntity<ToDo> getToDo(String toDoId) {
        ToDo toDo = null;
        if (toDoRepository.exists(toDoId)){
            logger.info("Retrieving Task ... !");
            toDo = toDoRepository.findOne(toDoId);
            logger.info("Retrieved Task with Id " + toDoId + " from Entity ToDo!");
            return new ResponseEntity<>(toDo, HttpStatus.OK);
        }
        else  {
            logger.warn("Task with ID " + toDoId + " does not exist in Entity ToDo!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * This method will delete the todo for provided taskId
     * @param id
     * @return void
     */

    public void deleteTask(String id) {
        if  (toDoRepository.exists(id)){
             logger.info("Deleting Task ... !");
             toDoRepository.delete(id);
             logger.info("Deleted Task with Id " + id + " from Entity ToDo!");
        }
        else if (id == null) {
            deleteAllToDos();
        }
        else {
            logger.warn("No Task with Id " + id + " exists in Entity ToDo!");
        }
    }

    /**
     * This method will retrieve all the todos from TODOList
     * @return  Iterable TODOObject
     */

    public Iterable<ToDo> getAllToDos() {
        Iterable<ToDo> iterateInstance = Collections.EMPTY_LIST;
        if  (toDoRepository.count() > 0) {
            Sort sortByCreatedAtDesc = new Sort(Sort.Direction.DESC, "createdAt");
            iterateInstance = toDoRepository.findAll();

            logger.info("Retrieved all Tasks from Entity ToDo!");
        }
        else {
            logger.warn("No task exists in Entity ToDo!");
        }
        return  iterateInstance;
    }

    /**
     * This Method will delete all todos for TODOList
     * @return
     */
    public void deleteAllToDos() {
        if (toDoRepository.count() > 0){
            logger.info("Deleting All Tasks ... ");
            toDoRepository.deleteAll();
            logger.info("Deleted All Tasks from Entity ToDo!");
        }
        else {
            logger.warn("No Task exists in Entity ToDo!");
        }
    }
}
