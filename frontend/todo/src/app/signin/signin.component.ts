import { Component, Injectable, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

@Injectable({
  providedIn: 'root'
})
@Component({
  selector: 'app-signin',
  templateUrl: './signin.component.html',
  styleUrls: ['./signin.component.scss']
})
export class SigninComponent implements OnInit {
  signinForm: FormGroup;
  username = new FormControl("")
  // password: ""
  // errorMessage = "Invalid Credentials"
  // invalidLogin = false

  constructor(
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit(): void {
  }
/*
  handleJWTAuthLogin(): any {
    this.basicAuthenticationService.executeJWTAuthenticationService(this.username, this.password)
      .subscribe(
        data => {
          console.log(data)
          console.log(this.username)
          //this.router.navigate(['welcome', this.username])
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
   handleJWTAuthLogin(): any {
     console.log(this.username);
   }

    onSubmit(form: any): void {
      console.log('you submitted value: ', form);
    }

}
