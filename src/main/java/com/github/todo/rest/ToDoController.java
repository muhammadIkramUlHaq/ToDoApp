package com.github.todo.rest;

/**
 * Created with IntelliJ IDEA.
 * User: Ikram
 * Date: 10/29/17
 * Time: 3:13 AM
 */

import com.github.todo.RestPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.github.todo.domain.ToDo;
import com.github.todo.service.ToDoService;
import javax.validation.Valid;


@RestController                    // This class is a RestController
@RequestMapping(RestPath.API)     // This means URL's start with /api this value (after Application path)
@CrossOrigin("*")                // The @CrossOrigin is used to enable Cross-Origin requests.
                                // This is required because we’ll be accessing the apis from angular’s frontend server
public class ToDoController {
    
    private ToDoService toDoService;
    private Logger logger = LoggerFactory.getLogger(ToDoService.class);

    @Autowired
    public ToDoController(ToDoService toDoService)
    {
        this.toDoService = toDoService;
    }

    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST or Update or Delete request


    /**
     * This method will call a service to get ALL TO DO tasks
     * @return   Iterable <To DO>
     */

    @GetMapping(RestPath.TODO)       // todo
    public @ResponseBody Iterable<ToDo> getAllToDos() {
        return toDoService.getAllToDos();
    }

    /**
     * This Method will call service to create new to do into ToDoList
     * @param todo  To Do
     * @return    To Do
     */

    @PostMapping(RestPath.TODO)
    public @ResponseBody ToDo createTodo(@Valid @RequestBody ToDo todo) {
       return toDoService.createToDo(todo);
    }


    /**
     * This Method will call the service to retrieve the To Do for provided To Do ID
     * @param id  String
     * @return        ResponseEntity<To Do>
     */

    @GetMapping(value=RestPath.TODO + "/{id}")
    public ResponseEntity<ToDo> getTodoById(@PathVariable("id") String id) {
        return toDoService.getToDo(id);

    }

    /**
     * This method will call service to update the task with provide taskId
     * @param id    String
     * @param todo    ToDo
     * @return          ResponseEntity<ToDo>
     */

    @PutMapping(value=RestPath.TODO + "/{id}")
    public ResponseEntity<ToDo> updateTodo(@PathVariable("id") String id,
                                           @Valid @RequestBody ToDo todo) {
       return toDoService.updateToDo(id,todo);
    }
    /**
     * This method will call the service to Delete a todo with provided ID
     * @param id  String
     * @return        void
     */
    @DeleteMapping(value=RestPath.TODO +"/{id}")
    public void deleteTodo(@PathVariable("id") String id) {
            toDoService.deleteTask(id);
    }
    

}