import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username = ""
  password = ""
  errorMessage = "Invalid Credentials"
  invalidLogin = false

  constructor(private router: Router,
    private basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit(): void {
  }

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


}
