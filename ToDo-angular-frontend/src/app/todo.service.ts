import { Injectable } from '@angular/core';
import { ToDo } from './todo';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

@Injectable()
export class TodoService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: Http) { }

  getTodos():  Promise<ToDo[]> {
    return this.http.get(this.baseUrl + '/api/todo/')
      .toPromise()
      .then(response => response.json() as ToDo[])
      .catch(this.handleError);
  }

  createTodo(todoData: ToDo): Promise<ToDo> {
    return this.http.post(this.baseUrl + '/api/todo/', todoData)
      .toPromise().then(response => response.json() as ToDo)
      .catch(this.handleError);
  }

  updateTodo(todoData: ToDo): Promise<ToDo> {
    return this.http.put(this.baseUrl + '/api/todo/' + todoData.id, todoData)
      .toPromise()
      .then(response => response.json() as ToDo)
      .catch(this.handleError);
  }

  deleteTodo(id: string): Promise<any> {
    return this.http.delete(this.baseUrl + '/api/todo/' + id)
      .toPromise()
      .catch(this.handleError);
  }

  private handleError(error: any): Promise<any> {
    console.error('Some error occured', error);
    return Promise.reject(error.message || error);
  }
}