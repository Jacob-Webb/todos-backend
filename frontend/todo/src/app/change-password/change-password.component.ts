import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserDataService } from '../service/data/user-data.service';

/*
* Receive a token from ResetPassword
* Call backend to check that the token is authenticated
* If the token is not authenticated for that user (error response), send to login page
* If the token is authenticated (non-error response), display change password form
*/

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.scss']
})
export class ChangePasswordComponent implements OnInit {
  token: string;
  changePasswordForm: FormGroup;

  constructor(
    private userService: UserDataService,
    private route: ActivatedRoute,
    private router: Router,
    fb:FormBuilder
   ) {
      this.changePasswordForm = fb.group({
       'password':['', Validators.required],
       'confirm-pass': ['', Validators.required]
     }),
      this.route.queryParams.subscribe(params => {
        this.token = params['token'];
      });
   }


  ngOnInit(): void {
    /* if this token is not valid, redirect to login page. */
      //confirm token
      if (this.token != null) {
        this.userService.confirmResetPasswordToken(this.token);
      }
      //this.router.navigate(['/login'], { queryParams: { token: this.token } });

  }

}
