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
  username: string;
  password: "";
  hide=true;
  invalidLogin = false;
  submitted=false;

  constructor(fb: FormBuilder,
              private router: Router,
              private basicAuthenticationService: BasicAuthenticationService) {
    this.signinForm = fb.group({
      'username':['', Validators.required],
      'password':['', Validators.required]
    });

    this.username = this.signinForm.controls['username'].value;
    this.password = this.signinForm.controls['password'].value;
  }

  ngOnInit(): void {
  }

  handleJWTAuthLogin(): any {
    this.username = this.signinForm.controls['username'].value;
    this.password = this.signinForm.controls['password'].value;
    this.basicAuthenticationService.executeJWTAuthenticationService(this.username, this.password)
      .subscribe(
        data => {
          this.router.navigate(['welcome', this.username])
          this.invalidLogin = false;
        },
        error => {
          this.invalidLogin = true;
          this.submitted = true;
        }
      )
    }
}
