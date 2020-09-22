import { Component } from '@angular/core';
import { BasicAuthenticationService } from '../service/basic-authentication.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent {

  constructor(public basicAuthenticationService: BasicAuthenticationService) { }

  ngOnInit(): void {}

}

