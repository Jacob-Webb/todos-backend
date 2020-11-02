import { Injectable } from '@angular/core';
import { ROLES } from './roles';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor() { }

  getRoles() {
    return Promise.resolve(ROLES);
  }
}
