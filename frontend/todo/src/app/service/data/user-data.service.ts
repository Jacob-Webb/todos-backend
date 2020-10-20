import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../../list-users/list-users.component';
import { API_URL, BASE_URL, REGISTER_URL, RESET_PASSWORD_URL } from 'src/app/app.constants';

@Injectable({
  providedIn: 'root'
})
export class UserDataService {

  constructor(private http: HttpClient) { }

  retrieveAllUsers() {
    return this.http.get<User[]>(`${API_URL}/users`)
  }

  deleteUser(id) {
    return this.http.delete(`${API_URL}/users/${id}`)
  }

  retrieveUser(id) {
    return this.http.get<User>(`${API_URL}/users/${id}`)
  }

  updateUser(id, user) {
    return this .http.put(`${API_URL}/users/${id}`, user)
  }

  createUser(user) {
    return this.http.post(`${API_URL}/users`, user);
  }

  registerUser(user) {
    return this.http.post(`${REGISTER_URL}`, user);
  }

  confirmUser(token) {
    return this.http.post(`${REGISTER_URL}/confirm`, token);
  }

  isNewUser(user) {
    return this.http.post(`${REGISTER_URL}/verify`, user);
  }

  resetPassword(email) {
    return this.http.post(`${RESET_PASSWORD_URL}`, email);
  }

  changePassword() {
    return this.http.get(`${RESET_PASSWORD_URL}/changePassword`);
  }

}
