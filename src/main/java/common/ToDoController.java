package common;

/**
 * Created with IntelliJ IDEA.
 * User: Ikram
 * Date: 10/29/17
 * Time: 3:13 AM
 */
 // ToDo : Make Logger Private, Use Http verb, Handle getALL and DeleteAll
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import common.ToDo;
import common.ToDoRepository;

@Controller    // This class is a Controller
@RequestMapping(path="/toDoApp") // This means URL's start with /toDoApp (after Application path)
public class ToDoController {
    @Autowired // This means to get the bean called toDoRepository

    private ToDoRepository toDoRepository;

    Logger logger = LoggerFactory.getLogger(ToDoController.class);
    // @ResponseBody means the returned String is the response, not a view name
    // @RequestParam means it is a parameter from the GET or POST or Update or Delete request

    /**
     * This Method will add new task into ToDoList
     * @param task  String
     * @return
     */
    @GetMapping(path="/add")
    public @ResponseBody String addNewTask (@RequestParam String task) {

        ToDo taskList = new ToDo();
        taskList.setTask(task);
        toDoRepository.save(taskList);
        logger.info("Saved new Task in Entity ToDo!");
        return "Saved";
    }

    /**
     * This method will update the task with provide taskId
     * @param taskId    Long
     * @param task      String
     * @return
     */
    @GetMapping(path="/update")
    public @ResponseBody String updateTask (@RequestParam Long taskId, @RequestParam String task) {
        String responseString = "";
        if (toDoRepository.exists(taskId)) {
            ToDo taskList = toDoRepository.findOne(taskId);
            taskList.setTask(task);
            toDoRepository.save(taskList);
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
     * This Method will return a JSON with all the Tasks
     * @return
     */
    @GetMapping(path="/getAll")
    public @ResponseBody Iterable<ToDo> getAllTasks() {
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
     * This Method will return a JSON with the Task for provided Task ID
     * @param taskId  Long
     * @return
     */
    @GetMapping(path="/get")
    public @ResponseBody ToDo getTask(@RequestParam Long taskId) {
        ToDo toDoInstance = null;
        if (toDoRepository.exists(taskId)){
           toDoInstance = toDoRepository.findOne(taskId);
           logger.info("Retrieved Task with Id " + taskId + " from Entity ToDo!");
        }
        else  {
            logger.warn("Task with ID " + taskId + " does not exist in Entity ToDo!");
        }
        return toDoInstance;
    }

    /**
     * This method will Delete a task with provided taskID
     * @param taskId  Long
     * @return
     */
    @GetMapping(path="/delete")
    public @ResponseBody String deleteTask(@RequestParam Long taskId) {
         String responseString = "";
         if  (toDoRepository.exists(taskId)){
               toDoRepository.delete(taskId);
               logger.info("Deleted Task with Id " + taskId + " from Entity ToDo!");
               responseString = "Deleted!";
         }
         else {
             logger.warn("No Task with Id " + taskId + " exists in Entity ToDo!");
             responseString = "Task with Given Id not Found!";
         }
         return responseString;
    }

    /**
     * This Method will deleted all the tasks from the ToDoList
     * @return
     */
    @GetMapping(path="/deleteAll")
    public @ResponseBody String deleteAllTask() {
         String responseString ="";
        if (toDoRepository.count() > 0){
            toDoRepository.deleteAll();
            logger.info("Deleted All Tasks from Entity ToDo!");
            responseString = "Deleted All Tasks!";
        }
        else {
            logger.warn("No Task exists in Entity ToDo!");
            responseString =  "No Task to be Deleted!";
        }
        return   responseString;
    }
}