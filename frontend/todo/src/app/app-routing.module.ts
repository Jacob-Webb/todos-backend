import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { ListTodosComponent } from './list-todos/list-todos.component';
import { ListUsersComponent } from './list-users/list-users.component';
import { LogoutComponent } from './logout/logout.component';
import {ErrorComponent } from './error/error.component';
import { RouteGuardService } from './service/route-guard.service';
import { TodoComponent } from './todo/todo.component';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { RecoverPasswordComponent } from './login/recover-password/recover-password.component';

// welcome
const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'login/recover', component: RecoverPasswordComponent},
  { path: 'login/:token', component: LoginComponent},
  { path: 'login', component: LoginComponent},
  { path: 'confirmation', component: ConfirmationComponent},
  { path: 'welcome/:name', component: WelcomeComponent, canActivate:[RouteGuardService]},
  { path: 'todos', component: ListTodosComponent, canActivate:[RouteGuardService]},
  { path: 'logout', component: LogoutComponent, canActivate:[RouteGuardService]},
  { path: 'todos/:id', component: TodoComponent, canActivate:[RouteGuardService]},
  { path: 'users', component: ListUsersComponent, canActivate:[RouteGuardService]},
  { path: '**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
