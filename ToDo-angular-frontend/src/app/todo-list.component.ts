import { Component, Input, OnInit } from '@angular/core';
import {TodoService} from './todo.service';
import { ToDo } from './todo';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'todo-list',
  templateUrl: './todo-list.component.html'
})

export class TodoListComponent implements OnInit {
  todos: ToDo[];
  newTodo: ToDo = new ToDo();
  editing: boolean = false;
  editingTodo: ToDo = new ToDo();

 constructor(
    private todoService: TodoService,
  ) {}


  ngOnInit(): void {
    this.getTodos();
  }

  getTodos(): void {
     this.todoService.getTodos()
      .then(todos => this.todos = todos ); 

  }

  createTodo(todoForm: NgForm): void {
      this.todoService.createTodo(this.newTodo)
       .then(createTodo => {        
          todoForm.reset();
          this.newTodo = new ToDo();
          this.todos.unshift(createTodo)
      });

  }

  deleteTodo(id: string): void {
      this.todoService.deleteTodo(id)
       .then(() => {
          this.todos = this.todos.filter(todo => todo.id != id);
      });
  }

  updateTodo(todoData: ToDo): void {
   console.log(todoData);
    this.todoService.updateTodo(todoData)
    .then(updatedTodo => {
      let existingTodo = this.todos.find(todo => todo.id === updatedTodo.id);
      Object.assign(existingTodo, updatedTodo);
      this.clearEditing();
    });

  }

  toggleCompleted(todoData: ToDo): void {

    todoData.completed = !todoData.completed;
    this.todoService.updateTodo(todoData)
    .then(updatedTodo => {
      let existingTodo = this.todos.find(todo => todo.id === updatedTodo.id);
      Object.assign(existingTodo, updatedTodo);
    });

  }

  editTodo(todoData: ToDo): void {
     this.editing = true;
    Object.assign(this.editingTodo, todoData);
  }

  clearEditing(): void {
    this.editingTodo = new ToDo();
    this.editing = false;
  }
}