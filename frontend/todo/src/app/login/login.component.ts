import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { SigninComponent } from './signin/signin.component';
import { RegisterComponent } from './register/register.component';
import { UserDataService } from '../service/data/user-data.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  private token: String;

  @Input() username: string;
  invalidLogin = false

  constructor(
    private signin: SigninComponent,
    private register: RegisterComponent,
    private userService: UserDataService,
    private route: ActivatedRoute,
    private router: Router,
    private basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit(): void {
    /*
    * If already logged in, route to Welcome page
    */


    /*
    * If there is a token,
    *   send to the backend to complete confirmation
    */
    this.token = this.route.snapshot.params['token'];
    if (this.token != null) {
      this.userService.confirmUser(this.token)
      .subscribe (
        data => {
          console.log(data);
        }
      )
    } else {
      console.log("no token");
    }
  }

}
