import { Component, OnInit } from '@angular/core';
import { TodoDataService } from '../service/data/todo-data.service';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

export class Todo {
  constructor(
    public id: number,
    public description: string,
    public done: boolean,
    public targetDate: Date
  ) {}
}

@Component({
  selector: 'app-list-todos',
  templateUrl: './list-todos.component.html',
  styleUrls: ['./list-todos.component.css']
})
export class ListTodosComponent implements OnInit {

  todos: Todo[];
  message: string;

  constructor(
    private todoService: TodoDataService,
    private router: Router,
    private basicAuthenticationService: BasicAuthenticationService
  ) { }

  ngOnInit(): void {
    this.refreshTodos();
  }

  refreshTodos() {
    this.todoService.retrieveAllTodos(this.basicAuthenticationService.getAuthenticatedUser()).subscribe(
      response => {
        console.log(response);
        this.todos = response;
      }
    )
  }

  updateTodo(id) {
    console.log(`update todo ${id}`)
    this.router.navigate(['todos', id])

  }

  deleteTodo(id) {
    console.log(`delete todo ${id}`);
    this.todoService.deleteTodo(this.basicAuthenticationService.getAuthenticatedUser(), id).subscribe(
      response => {
        console.log(response);
        this.message = `Delete of Todo ${id} Succesful`
        this.refreshTodos();
      }
    );

  }

  createTodo(){
    this.router.navigate(['todos',-1]);
  }

}
