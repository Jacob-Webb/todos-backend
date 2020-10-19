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
                 'email':['', Validators.required]
               })
             }

  ngOnInit(): void {
  }

  submitEmail() {

  }

}
