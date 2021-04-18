import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {LoginModel} from '../../../models/auth.model';
import {AddUserData} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';
import Swal from "sweetalert2";
import {HospitalService} from '../../../services/hospital.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.css']
})
export class AddUserComponent implements OnInit {

  addUserForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    role: new FormControl('', Validators.required),
    hospital: new FormControl('', Validators.required)
  });

  public hospitals: Array<any> = [];

  constructor(
    private userService: UserService,
    private hospitalService: HospitalService
  ) { }

  ngOnInit(): void {
    this.hospitalService.getAllHospitals().subscribe((response) => {
      this.hospitals = response;
    })
  }

  addUser() {
    const addUserDTO: AddUserData = {
      email: this.addUserForm.value.email,
      password: this.addUserForm.value.password,
      firstName: this.addUserForm.value.firstName,
      lastName: this.addUserForm.value.lastName,
      hospital: this.addUserForm.value.hospital,
      role: this.addUserForm.value.role
    };

    this.userService.addUser(addUserDTO).subscribe((response) => {
      Swal.fire({
        icon: 'success',
        title: 'Success!',
        text: 'User successfully added!'
      })
      this.addUserForm.reset();
    }, error => {
      console.log(error);
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'User with submitted email already exists!',
        confirmButtonColor: '#DC143C'
      })
    })
  }
}
