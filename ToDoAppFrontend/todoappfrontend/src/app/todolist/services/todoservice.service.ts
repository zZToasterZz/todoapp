import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http'
import { throwError, Observable } from 'rxjs';
import {catchError} from 'rxjs/operators';
import {Todo} from './Todo'

@Injectable({
  providedIn: 'root'
})
export class TodoserviceService
{
  baseUrl='http://localhost:9090/todo';

  constructor(private http:HttpClient) { }

  fetchTodo()
  {
    return this.http.get(this.baseUrl+'/fetch');
  }

  addTodo(data:Todo): Observable<Todo>
  {
    return this.http.post<Todo>(this.baseUrl+'/add', data).pipe(
      catchError(this.handleError)
    );
  }

  editTodo(data:Todo)
  {
    return this.http.put<Todo>(this.baseUrl+'/update', data).pipe(
      catchError(this.handleError)
    );
  }

  completeTodo(id:string)
  {
    var dummy:Todo = new Todo();
    return this.http.patch<Todo>(this.baseUrl+'/complete/'+id, dummy).pipe(
      catchError(this.handleError)
    );
  }

  deleteTodo(id:string)
  {
    return this.http.delete<Todo>(this.baseUrl+'/delete/'+id).pipe(
      catchError(this.handleError)
    );
  }

  handleError(error:HttpErrorResponse)
  {
    console.log(error);
    return throwError('some error occurred');
  }
}