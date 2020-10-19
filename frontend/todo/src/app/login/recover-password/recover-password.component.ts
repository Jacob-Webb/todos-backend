import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.scss']
})
export class RecoverPasswordComponent implements OnInit {
  recoverForm: FormGroup

  constructor(
              fb:FormBuilder
             ) {
               this.recoverForm = fb.group({
                 'email':['', Validators.compose([Validators.email, Validators.required])]
               })
             }

  ngOnInit(): void {
  }

  getEmailError() {
    if (this.recoverForm.controls['email'].hasError('required')) {
      return 'You must enter a value';
    }

    return this.recoverForm.controls['email'].hasError('email') ? 'Not a valid email' : '';
  }

  submitEmail() {
    console.log(this.recoverForm.controls['email'].value);
  }

}
