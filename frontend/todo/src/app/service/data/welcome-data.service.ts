import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BasicAuthenticationService } from '../basic-authentication.service';

export class HelloWorldBean {
  constructor(public message:string) {}
}

@Injectable({
  providedIn: 'root'
})
export class WelcomeDataService {

  constructor(
    private http:HttpClient,
    private basicAuthenticationService: BasicAuthenticationService
  ) { }

  executeHelloWorldBeanService() {
    return this.http.get<HelloWorldBean>('http://localhost:8080/hello-world-bean');
    //console.log("Execute hello World Bean Service")
  }

  executeHelloWorldBeanServiceWithPathVariable(name) {
    // let basicAuthHeaderString = this.createBasicAuthenticationHttpHeader();

    // let headers = new HttpHeaders({
    //   Authorization: basicAuthHeaderString
    // })
    if (this.basicAuthenticationService.isUserLoggedIn())
      return this.http.get<HelloWorldBean>(`http://localhost:8080/hello-world-bean/path-variable/${name}`,
    //{headers}
    );
  }

  // createBasicAuthenticationHttpHeader() {
  //   let username = 'webbj';
  //   let password = 'dummy';
  //   let basicAuthHeaderString = 'Basic ' + window.btoa(username + ':' + password);

  //   return basicAuthHeaderString;
  // }
}
