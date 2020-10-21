import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDataService } from 'src/app/service/data/user-data.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
  recoverForm: FormGroup;
  email: string;
  token: string;

  constructor(
              private userService: UserDataService,
              private route: ActivatedRoute,
              private router: Router,
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
    this.email = '';

    if (this.token != '') {
      this.router.navigate(['/changePassword'], { queryParams: { token: this.token } });
    }
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