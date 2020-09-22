import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators} from '@angular/forms';
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

    signin: FormGroup = new FormGroup({
      email: new FormControl('', [Validators.email, Validators.required ]),
      password: new FormControl('', [Validators.required, Validators.min(5) ])
    });
    hide = true;
    get emailInput() { return this.signin.get('email'); }
    get passwordInput() { return this.signin.get('password'); }
}
