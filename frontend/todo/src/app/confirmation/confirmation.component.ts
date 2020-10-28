import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../list-users/list-users.component';
import { RegisterComponent } from '../login/register/register.component';
import { UserDataService } from '../service/data/user-data.service';
import { SessionService } from '../service/session.service';

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.scss']
})
export class ConfirmationComponent implements OnInit {
  message: string;
  user: User;
  userEmail: string;

  constructor(sessionService: SessionService,
              private route: ActivatedRoute,
              private userService: UserDataService) {
    this.user = sessionService.user;
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params=> {
      let idParam = params['a'];
      this.userEmail = atob(decodeURIComponent(idParam));
    });
    this.userService.registerUser(this.user).pipe(
      catchError((error)=>{
        if (error.status) {
            console.log("An unexpected error occurred");
            return throwError(error.status);
        }
    })
    ).subscribe(
      //if this is null let them know that the person just needs to be enabled
      resp => {
        if (resp.status === 202){

        } else if (resp.status === 201) {

        }
      }
    )
  }

}
