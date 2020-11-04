import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../list-users/list-users.component';
import { AUTHENTICATED_USER, BasicAuthenticationService } from '../service/basic-authentication.service';
import { UserDataService } from '../service/data/user-data.service';
import { IConfig } from 'ngx-mask'

@Component({
  selector: 'app-user-account',
  templateUrl: './user-account.component.html',
  styleUrls: ['./user-account.component.scss']
})
export class UserAccountComponent implements OnInit {
  user: User;
  isUniqueEmail: boolean = true;
  hide: boolean = false;
  hideConfirm: boolean = false;
  passwordMinLength = 3;
  updateUserForm: FormGroup;
  currentPassword: string;

  constructor(private basicAuthorizationService: BasicAuthenticationService,
              private userDataService: UserDataService,
              fb: FormBuilder) {
                this.updateUserForm = fb.group({
                  'firstName':['', Validators.compose([Validators.maxLength(20), Validators.pattern('[a-zA-Z]*'), Validators.required])],
                  'lastName':['', Validators.compose([Validators.maxLength(20), Validators.pattern('[a-zA-Z]*'), Validators.required])],
                  'email':['', Validators.compose([Validators.email, Validators.required])],
                  'phone':['', Validators.compose([])]
                },{
                  // check whether our password and confirm password match
                  validator: this.passwordMatchValidator
               });
              }

  ngOnInit(): void {
    this.userDataService.retrieveUserByEmail(localStorage.getItem(AUTHENTICATED_USER)).subscribe(
      response => {
        this.user = response;
        this.updateUserForm.controls['firstName'].setValue(response.firstName)
        this.updateUserForm.controls['lastName'].setValue(response.lastName)
        this.updateUserForm.controls['email'].setValue(response.email)
        this.updateUserForm.controls['phone'].setValue(response.phone)
      })
  }

  onSubmit() {
    console.log(this.updateUserForm.controls['firstName'].value)
  }

  getEmailError() {
    if (this.updateUserForm.controls['email'].hasError('required')) {
      return 'You must enter a value';
    }

    return this.updateUserForm.controls['email'].hasError('email') ? 'Not a valid email' : '';
  }

  getFirstNameError() {
    if (this.updateUserForm.controls['firstName'].hasError('required')) {
      return 'First name is required';
    }
    return this.updateUserForm.controls['firstName'].hasError('pattern') ? 'Name can only contain letters' : '';
  }

  getLastNameError() {
    if (this.updateUserForm.controls['lastName'].hasError('required')) {
      return 'Last name is required';
    }
    return this.updateUserForm.controls['lastName'].hasError('pattern') ? 'Name can only contain letters' : '';
  }

}
