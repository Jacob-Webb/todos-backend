import { Component, Injectable, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
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
  username: string;
  password: "";
  hide=true;
  // errorMessage = "Invalid Credentials"
  invalidLogin = false;
  submitted=false;

  constructor(fb: FormBuilder,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) {
    this.signinForm = fb.group({
      'username':['', Validators.required],
      'password':['', Validators.required]
    });

    this.username = this.signinForm.controls['username'].value;
    this.password = this.signinForm.controls['password'].value;
  }

  ngOnInit(): void {
  }

  handleJWTAuthLogin(): any {
    this.username = this.signinForm.controls['username'].value;
    this.password = this.signinForm.controls['password'].value;
    this.basicAuthenticationService.executeJWTAuthenticationService(this.username, this.password)
      .subscribe(
        data => {
          console.log(data)
          console.log(this.username)
          this.router.navigate(['welcome', this.username])
          this.invalidLogin = false;
        },
        error => {
          console.log("didn't make it")
          console.log(error)
          this.invalidLogin = true;
          this.submitted = true;
          this.signinForm.controls['username'].setValue('');
          this.signinForm.controls['password'].setValue('');
        }
      )
    }


    onSubmit(value: string): void {
      this.username = this.signinForm.controls['username'].value;
      this.password = this.signinForm.controls['password'].value;
      console.log(this.username + " " + this.password);
    }

}
