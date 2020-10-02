import { Component, OnInit } from '@angular/core';
import { UserDataService } from '../service/data/user-data.service';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

export class User {
  constructor(
    public id: number,
    public username: string,
    public password: string,
    public firstName: string,
    public lastName: string,
    public enabled: boolean,
    public email: string,
    public phone: string,
    public roles: string[]
  ) {}

}

@Component({
  selector: 'app-list-users',
  templateUrl: './list-users.component.html',
  styleUrls: ['./list-users.component.css']
})
export class ListUsersComponent implements OnInit {
  users: User[];
  message: string;

  constructor(private userService: UserDataService,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService
              ) { }

  ngOnInit(): void {
    this.refreshUsers();
  }

  refreshUsers() {
    this.userService.retrieveAllUsers().subscribe(
      response => {
        console.log(response);
        this.users = response;
      }
    )
  }

  updateUser(id) {
    console.log(`update user ${id}`)
    this.router.navigate(['users', id])
  }

  deleteTodo(id) {
    console.log(`delete user ${id}`);
    this.userService.deleteUser(id).subscribe(
      response => {
        console.log(response);
        this.message = `Deleted User ${id} successful`
        this.refreshUsers();
      }
    );
  }

  createUser() {
    this.router.navigate(['users', -1]);
  }

}
