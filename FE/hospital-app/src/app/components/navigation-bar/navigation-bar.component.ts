import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  constructor(private route: Router, private authService: AuthService) { }

  role = '';

  ngOnInit(): void {
    this.role = this.authService.getUserAuthorities()[0].authority;
  }

  logout() {
    localStorage.removeItem("accessToken");
    this.route.navigate(['/'])
  }
}
