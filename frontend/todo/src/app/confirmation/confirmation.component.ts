import { Component, Input, OnInit } from '@angular/core';
import { User } from '../list-users/list-users.component';
import { RegisterComponent } from '../login/register/register.component';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent implements OnInit {
  message: string;
  user: User;

  constructor() {
  }

  ngOnInit(): void {
  }

}
