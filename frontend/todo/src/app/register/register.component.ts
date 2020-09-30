import { Component, Injectable, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

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
  username: string;
  password: string;
  email: string;
  phone: string;
  hide=true;
  submitted=false;

  constructor(fb: FormBuilder) {
    this.registerForm = fb.group({
      'firstName':['', Validators.required],
      'lastName':['', Validators.required],
      'userName':['', Validators.required],
      'password':['', Validators.required],
      'email':['', Validators.required],
      'phone':['']
    });
   }

  ngOnInit(): void {
  }

  onSubmit(): void {
    this.firstName = this.registerForm.controls['firstName'].value;
    this.lastName = this.registerForm.controls['lastName'].value;

    console.log("submitted");
  }

}
