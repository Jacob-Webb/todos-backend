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
  email: string;
  password: string;
  phone: string;
  hide=true;
  submitted=false;

  constructor(fb: FormBuilder,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) {
    this.registerForm = fb.group({
      'firstName':['', Validators.required],
      'lastName':['', Validators.required],
      'email':['', Validators.required],
      'phone':[''],
      'new-password':['', Validators.required],
      'confirm-password':['', Validators.required]
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
    this.firstName = this.registerForm.controls['firstName'].value;
    this.lastName = this.registerForm.controls['lastName'].value;

    console.log("submitted");
  }

}
