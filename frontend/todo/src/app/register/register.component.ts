import { Component, Injectable, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators } from '@angular/forms';
import { NgxMaskModule, IConfig } from 'ngx-mask'
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication.service';
import { User } from '../list-users/list-users.component';
import { UserDataService } from '../service/data/user-data.service';
import { catchError, map, tap } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { throwError } from 'rxjs';
import { HttpResponse } from '@angular/common/http';

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
  passwordMinLength = 3;
  phone: string;
  hide=true;
  hideConfirm=true;
  submitted=false;
  isUniqueEmail=true;
  user: User;

  constructor(private userService: UserDataService,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService,
              fb: FormBuilder
             ) {
    this.registerForm = fb.group({
      'firstName':['', Validators.compose([Validators.maxLength(20), Validators.pattern('[a-zA-Z]*'), Validators.required])],
      'lastName':['', Validators.compose([Validators.maxLength(20), Validators.pattern('[a-zA-Z]*'), Validators.required])],
      'email':['', Validators.compose([Validators.email, Validators.required])],
      'phone':['', Validators.compose([])],
      'new-password':['', Validators.compose([Validators.minLength(3), Validators.required])],
      'confirm-password':['', Validators.compose([Validators.minLength(3), Validators.required])]
    },{
      // check whether our password and confirm password match
      validator: this.passwordMatchValidator
    });

   }

  ngOnInit(): void {
  }

  onSubmit(): void {
    /*
    * create a user with controls
    * send the user via http
    */
   this.user = new User(
                   this.registerForm.controls['firstName'].value,
                   this.registerForm.controls['lastName'].value,
                   this.registerForm.controls['email'].value,
                   this.registerForm.controls['new-password'].value,
                   this.registerForm.controls['phone'].value,
                   )
    this.userService.registerUser(this.user).pipe(
      /*tap((resp: HttpResponse<any>)=> {

          if  (resp.status === 201) {
            console.log("created")
          } else if (resp.status === 202) {
            console.log("accepted");
          }

      }),
      */
      catchError((error)=>{
        if (error.status === 500) {
            console.log("unexpected error");
            return throwError(error.status);
        }
        // or do something with this error code since the person exists
        else if (error.status === 409) {
          this.isUniqueEmail = false;
          console.log(this.isUniqueEmail);
          return throwError(error.status);
        }
        else if (error.status === 406) {
            return throwError(error.status);
        }
    })
    ).subscribe(
      //if this is null let them know that the person just needs to be enabled
      data => {
        if (data == null) {
          console.log("do something with this");
        }
      }

    )

  }

  passwordMatchValidator(formGroup: FormGroup) {
    const password: string = formGroup.get('new-password').value; // get password from our password form control
    const confirmPassword: string = formGroup.get('confirm-password').value; // get password from our confirmPassword form control
    // compare is the password math
    if (password !== confirmPassword) {
      // if they don't match, set an error in our confirmPassword form control
      formGroup.get('confirm-password').setErrors({ NoPassswordMatch: true });
    }
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
