import { Component, Injectable, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { NgxMaskModule, IConfig } from 'ngx-mask'
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

export const options: Partial<IConfig> | (() => Partial<IConfig>) = null;

@Injectable({
  providedIn:'root'
})
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  phone: string;
  hide=true;
  submitted=false;

  constructor(fb: FormBuilder,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) {
    this.registerForm = fb.group({
      'firstName':['', Validators.compose([Validators.maxLength(20), Validators.pattern('[a-zA-Z]*'), Validators.required])],
      'lastName':['', Validators.compose([Validators.maxLength(20), Validators.pattern('[a-zA-Z]*'), Validators.required])],
      'email':['', Validators.compose([Validators.email, Validators.required])],
      'phone':['', Validators.compose([])],
      'new-password':['', Validators.compose([Validators.minLength(3), Validators.required])],
      'confirm-password':['', Validators.compose([Validators.minLength(3), Validators.required])]
    });
   }

  ngOnInit(): void {
  }

  onSubmit(): void {

    /*
      Requirements:
        - username has to be unique
        - password must be greater than 3 letters, and contain an uppercase letter, lowercase letter, at least one number, and at least one special symbol
        - confirm password
    */
    // this.firstName = this.registerForm.controls['firstName'].value;
     this.phone = this.registerForm.controls['phone'].value;

    console.log(this.phone);
  }

  getEmailError() {
    if (this.registerForm.controls['email'].hasError('required')) {
      return 'You must enter a value';
    }

    return this.registerForm.controls['email'].hasError('email') ? 'Not a valid email' : '';
  }

  getFirstNameError() {
    if (this.registerForm.controls['firstName'].hasError('required')) {
      return 'First name is required';
    }
    return this.registerForm.controls['firstName'].hasError('pattern') ? 'Name can only contain letters' : '';
  }

  getLastNameError() {
    if (this.registerForm.controls['lastName'].hasError('required')) {
      return 'Last name is required';
    }
    return this.registerForm.controls['lastName'].hasError('pattern') ? 'Name can only contain letters' : '';
  }
}
