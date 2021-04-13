import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginModel} from '../../models/auth.model';
import {AuthService} from '../../services/auth.service';
import {Router} from '@angular/router';
import Swal from 'sweetalert2';

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

    this.service.login(loginDto).subscribe((response) => {
      localStorage.setItem("accessToken", response.authenticationToken);
      this.route.navigate(['/admin-panel']);
    }, error => {
      if (error.status === 403) {
        Swal.fire({
          icon: 'error',
          title: 'User DISABLED',
          text: 'We encountered some suspicious activities from this account. We sent you an email, please check it in order to continue.',
          confirmButtonColor: '#DC143C'
        })
      }
      else {
        Swal.fire({
          icon: 'error',
          title: 'User not found',
          text: 'Please check your email and password!',
          confirmButtonColor: '#DC143C'
        })
      }
    });
  }
}
