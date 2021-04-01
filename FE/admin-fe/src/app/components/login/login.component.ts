import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginModel} from '../../models/auth.model';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required)
  });

  constructor(
    private service: AuthService,
    private route: Router
  ) { }

  ngOnInit(): void {
  }

  loginUser(): any {
    const loginDto: LoginModel = {
      email: this.loginForm.value.email,
      password: this.loginForm.value.password
    };

    // this.service.login(loginDto).subscribe((response) => {
    this.route.navigate(['/admin-panel']);
    // }, error => {
    //
    // });
  }
}
