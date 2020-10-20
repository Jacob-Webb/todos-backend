import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { UserDataService } from 'src/app/service/data/user-data.service';

@Component({
  selector: 'app-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.scss']
})
export class RecoverPasswordComponent implements OnInit {
  recoverForm: FormGroup;
  email: string;
  token: string;

  constructor(
              private userService: UserDataService,
              private route: ActivatedRoute,
              fb:FormBuilder
             ) {
                this.recoverForm = fb.group({
                 'email':['', Validators.compose([Validators.email, Validators.required])]
               }),
                this.route.queryParams.subscribe(params => {
                this.token = params['token'];
                });
             }

  ngOnInit(): void {
    if (this.token != null) console.log(this.token);

  }

  getEmailError() {
    if (this.recoverForm.controls['email'].hasError('required')) {
      return 'You must enter a value';
    }

    return this.recoverForm.controls['email'].hasError('email') ? 'Not a valid email' : '';
  }

  submitEmail() {
    this.email = this.recoverForm.controls['email'].value;
    this.userService.resetPassword(this.email).subscribe(
      data => {
        console.log(data);
      }
    )
  }

}
