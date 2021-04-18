import { Component, OnInit } from '@angular/core';
import {UserDetails} from '../../../models/user.model';
import {PageEvent} from '@angular/material/paginator';
import {UserService} from '../../../services/user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit {

  public admins: Array<UserDetails> =  [];
  public doctors: Array<UserDetails> =  [];

  a_length = 0;
  a_pageSize = 10;
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
    this.userService.changeAuthority(3).subscribe((response) => {
      console.log(response);
    })
    this.userService.getAllAdmins(this.a_pageIndex).subscribe((response) => {
      this.admins = response.content;
      this.a_length = response.totalElements;
    })
  }

  d_handlePageEvent(event: PageEvent) {
    this.d_length = event.length;
    this.d_pageIndex = event.pageIndex;

    this.userService.getAllDoctors(this.d_pageIndex).subscribe((response) => {
      this.doctors = response;
      this.d_length = this.doctors.length;
    })
  }

}
