import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {ChangePasswordModel, UserDetails} from '../../models/user.model';
import {TokenModel} from '../../models/auth.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import Swal from "sweetalert2";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public user: UserDetails = {
    id: 0,
    email: "",
    firstName: "",
    lastName: "",
    hospital: "",
    enabled: true
  };

  public notSame: boolean = false;

  changePasswordForm = new FormGroup({
    oldPassword: new FormControl('', [Validators.required]),
    newPassword: new FormControl('', [Validators.required, Validators.pattern("\"^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@.#\\$%\\^&\\*])(?=.{8,})\"")]),
    repeatNewPassword: new FormControl('', Validators.required),
  });

  constructor(
    private authService: AuthService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    let token = this.authService.getToken();
    let data: TokenModel = this.authService.decodeToken(token);

    this.user.email = data.sub;
    this.user.firstName = data.user_firstName;
    this.user.id = Number(data.user_id);
    this.user.lastName = data.user_lastName;

    //AUTHORITY
    this.user.hospital = data.authority[0].name;
  }

  changePassword() {
    let old = this.changePasswordForm.value.oldPassword;
    let newP = this.changePasswordForm.value.newPassword;
    let newRep = this.changePasswordForm.value.repeatNewPassword;

    if(newP != newRep) {
      this.notSame = true;
    } else {
      this.notSame = false;

      const data: ChangePasswordModel = {
        oldPassword: old,
        newPassword: newP
      }

      this.userService.changePassword(data).subscribe((response) => {
        Swal.fire({
          icon: 'success',
          title: 'Success!',
          text: 'Your password is successfully changed!'
        })
        this.changePasswordForm.reset();
      }, error => {
        if (error.status === 412) {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'The old password doesn\'t match with password in our database. Please check your password and try again.',
            confirmButtonColor: '#DC143C'
          })
        } else {
          Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'Something went wrong! Please try again later',
            confirmButtonColor: '#DC143C'
          })
        }
      })
    }
  }
}
