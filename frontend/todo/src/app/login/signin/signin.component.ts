import { Component, Injectable, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../../service/basic-authentication.service';

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
  email: string;
  password: "";
  hide=true;
  invalidLogin = false;
  submitted=false;

  constructor(fb: FormBuilder,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) {
    this.signinForm = fb.group({
      'email':['', Validators.required],
      'password':['', Validators.required]
    });

    this.email = this.signinForm.controls['email'].value;
    this.password = this.signinForm.controls['password'].value;
  }

  ngOnInit(): void {
  }

  handleJWTAuthLogin(): any {
    this.email = this.signinForm.controls['email'].value;
    this.password = this.signinForm.controls['password'].value;
    this.basicAuthenticationService.executeJWTAuthenticationService(this.email, this.password)
      .subscribe(
        data => {
          this.router.navigate(['welcome', this.email])
          this.invalidLogin = false;
        },
        error => {
          this.invalidLogin = true;
          this.submitted = true;
        }
      )
    }
}
