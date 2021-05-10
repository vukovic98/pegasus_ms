import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AddUserData} from '../../../models/user.model';
import {UserService} from '../../../services/user.service';
import Swal from "sweetalert2";

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
    hospital: new FormControl( {value: 'GSM Hospital', disabled: true}, Validators.required)
  });

  public hospital: string = "GSM Hospital";
  private mostCommonPasswords = ['L58jkdjP!', 'P@ssw0rd', '!QAZ2wsx', '1qaz!QAZ', '1qaz@WSX', 'ZAQ!2wsx',
    '!QAZxsw2', 'xxPa33bq.aDNA', '!QAZ1qaz', 'g00dPa$$w0rD', 'Jhon@ta2011', '1qazZAQ!'];
  public validPassword: boolean = true;

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
  }

  addUser() {
    if(this.validPassword) {
      const addUserDTO: AddUserData = {
        email: this.addUserForm.value.email,
        password: this.addUserForm.value.password,
        firstName: this.addUserForm.value.firstName,
        lastName: this.addUserForm.value.lastName,
        //hospital: this.addUserForm.value.hospital,
        hospital: this.hospital,
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
    } else {
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'It appears that your password is too weak, or is on the \'Most common passwords\' list! Please select stronger, more unique password.',
        confirmButtonColor: '#DC143C'
      })
    }
  }

  testPassword(event: any) {
    const strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@.#\$%\^&\*])(?=.{8,})");

    if(!strongRegex.test(event.target.value)) {
      event.target.classList.remove("alert-success")
      event.target.classList.add("alert-danger")
      this.validPassword = false;
    } else {
      if(this.mostCommonPasswords.indexOf(event.target.value) >= 0) {
        event.target.classList.remove("alert-success")
        event.target.classList.add("alert-danger")
        this.validPassword = false;
      } else {
        event.target.classList.remove("alert-danger")
        event.target.classList.add("alert-success")
        this.validPassword = true;
      }
    }
  }
}
