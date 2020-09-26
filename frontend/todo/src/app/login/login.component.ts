import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { SigninComponent } from '../signin/signin.component';
import { RegisterComponent } from '../register/register.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  @Input() username: string;
  invalidLogin = false

  constructor(public signin: SigninComponent,
              public register: RegisterComponent,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit(): void {
  }

}
