package com.github.todo.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.todo.RestPath;
import com.github.todo.domain.ToDo;
import com.github.todo.service.ToDoService;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ToDoService toDoService;

    @Test
    public void should_create_todo() throws Exception
    {
        // GIVEN:
        String id = "1";
        String title = "Title 1";
        boolean completed = false;
        Date date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        ToDo expectedTodo = ToDo.newBuilder().id(id).title(title).completed(completed).createdAt(date).build();

        String todoJsonString = "{\"id\":\"1\",\"title\":\"Title 1\",\"completed\":\"false\",\"createdAt\":null}";

        // toDoService.createToDo to respond back with mockCourse
        Mockito.when(
                toDoService.createToDo(Mockito.any(ToDo.class))).thenReturn(expectedTodo);

        // Send ToDo at localhost/api/todo/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(RestPath.API+"/"+RestPath.TODO)
                .accept(MediaType.APPLICATION_JSON).content(todoJsonString)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/api/todo/",
                response.getHeader(HttpHeaders.LOCATION));

    }


    @Test
    public void should_fail_to_create_todo_when_title_is_null() throws Exception
    {
        // GIVEN:
        String id = "1";
        String title = null;
        boolean completed = false;
        Date date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        ToDo expectedTodo = ToDo.newBuilder().id(id).title(title).completed(completed).createdAt(date).build();

        String todoJsonString = "{\"id\":\"1\",\"title\":null,\"completed\":\"false\",\"createdAt\":null}";

        // toDoService.createToDo to respond back with mockCourse
        Mockito.when(
                toDoService.createToDo(Mockito.any(ToDo.class))).thenReturn(expectedTodo);

        // Send ToDo at localhost/api/todo/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(RestPath.API+"/"+RestPath.TODO)
                .accept(MediaType.APPLICATION_JSON).content(todoJsonString)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/api/todo/",
                response.getHeader(HttpHeaders.LOCATION));
        
    }

    @Test
    public void should_fail_to_create_todo_when_title_is_empty() throws Exception
    {
        
        // GIVEN:
        String id = "1";
        String title = "";
        boolean completed = false;
        Date date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        ToDo expectedTodo = ToDo.newBuilder().id(id).title(title).completed(completed).createdAt(date).build();

        String todoJsonString = "{\"id\":\"1\",\"title\":\"\",\"completed\":\"false\",\"createdAt\":null}";

        // toDoService.createToDo to respond back with mockCourse
        Mockito.when(
                toDoService.createToDo(Mockito.any(ToDo.class))).thenReturn(expectedTodo);

        // Send ToDo at localhost/api/todo/
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(RestPath.API+"/"+RestPath.TODO)
                .accept(MediaType.APPLICATION_JSON).content(todoJsonString)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

        assertEquals("http://localhost/api/todo/",
                response.getHeader(HttpHeaders.LOCATION));

    }

    @Test
    public void should_get_todo_for_given_id() throws Exception {

        // GIVEN:
        String id = "1";
        String title = "Title 1";
        boolean completed = false;
        Date date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        ResponseEntity<ToDo> mockToDo = null;// ToDo.newBuilder().id(id).title(title).completed(completed).createdAt(date).build();
        String toDoJson = "{\"id\":\"1\",\"title\":\"Title 1\",\"completed\":false,\"createdAt\":null}";

        Mockito.when(
                toDoService.getToDo(Mockito.anyString())).thenReturn(mockToDo);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(RestPath.API  + RestPath.TODO + "/" + id).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        System.out.println(result.getResponse());
        String expected = "{id:1,title:Title 1,completed:false,createdAt:null}";

        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
}