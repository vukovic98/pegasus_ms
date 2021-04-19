import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {UserDetails} from '../../models/user.model';
import {TokenModel} from '../../models/auth.model';
import {FormControl, FormGroup, Validators} from '@angular/forms';

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
    newPassword: new FormControl('', Validators.required),
    repeatNewPassword: new FormControl('', Validators.required),
  });

  constructor(
    private authService: AuthService
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
      // TODO service logic
      this.notSame = false;
    }
  }
}
