import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { SigninComponent } from '../signin/signin.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  @Input() username: string;
  invalidLogin = false

  constructor(public signin: SigninComponent,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit(): void {
  }

  route () {

  }

  /*
  handleJWTAuthLogin() {
    this.basicAuthenticationService.executeJWTAuthenticationService(this.username, this.password)
      .subscribe(
        data => {
          console.log(data)
          this.router.navigate(['welcome', this.username])
          this.invalidLogin = false;
        },
        error => {
          console.log("didn't make it")
          console.log(error)
          this.invalidLogin = true
        }
      )
    }
    */


}
