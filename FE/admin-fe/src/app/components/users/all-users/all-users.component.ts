import { Component, OnInit } from '@angular/core';
import {UserDetails} from '../../../models/user.model';
import {PageEvent} from '@angular/material/paginator';
import {UserService} from '../../../services/user.service';
import Swal from "sweetalert2";

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  public admins: Array<UserDetails> =  [];
  public doctors: Array<UserDetails> =  [];

  a_length = 0;
  a_pageSize = 8;
  a_pageIndex = 0;
  a_showFirstLastButtons = true;

  d_length = 0;
  d_pageSize = 10;
  d_pageIndex = 0;
  d_showFirstLastButtons = true;

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.userService.getAllAdmins(this.a_pageIndex).subscribe((response) => {
      this.admins = response.content;
      this.a_length = response.totalElements;
    })

    this.userService.getAllDoctors(this.d_pageIndex).subscribe((response) => {
      this.doctors = response.content;
      this.d_length = response.totalElements;
    })
  }

  a_handlePageEvent(event: PageEvent) {
    this.a_length = event.length;
    this.a_pageIndex = event.pageIndex;

    this.userService.getAllAdmins(this.a_pageIndex).subscribe((response) => {
      this.admins = response.content;
      this.a_length = response.totalElements;
    })
  }

  d_handlePageEvent(event: PageEvent) {
    this.d_length = event.length;
    this.d_pageIndex = event.pageIndex;

    this.userService.getAllDoctors(this.d_pageIndex).subscribe((response) => {
      this.doctors = response.content;
      this.d_length = response.totalElements;
    })
  }

  changeAuthority(id: number) {
    this.userService.changeAuthority(id).subscribe((response) => {
      Swal.fire({
        icon: 'success',
        title: 'Success!',
        text: 'Users authority successfully changed!'
      })

      this.loadData();
    }, error => {
      if (error.status === 412) {
        Swal.fire({
          icon: 'error',
          title: 'Not Allowed',
          text: 'This user was the only administrator in his hospital. Please add more administrators and then change users authority.',
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

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe((response) => {
      Swal.fire({
        icon: 'success',
        title: 'Success!',
        text: 'Users authority successfully changed!'
      })
      this.loadData();
    }, error => {
      if (error.status === 412) {
        Swal.fire({
          icon: 'error',
          title: 'Not Allowed',
          text: 'This user was the only administrator in his hospital. Please add more administrators and then delete user.',
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
