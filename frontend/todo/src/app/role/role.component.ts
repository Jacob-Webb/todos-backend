import { Component, OnInit } from '@angular/core';
import { Role } from './shared/role.model';
import { RoleService } from './shared/role.service';

@Component({
  selector: 'app-role',
  templateUrl: './role.component.html',
  styleUrls: ['./role.component.css']
})
export class RoleComponent implements OnInit {
  roles: Role[];

  constructor(private roleService: RoleService) { }

  ngOnInit(): void {
    this.roleService.getRoles()
    .then(roles => this.roles = roles);
  }

}
