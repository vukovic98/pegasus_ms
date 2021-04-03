import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {

  constructor(private route: Router) { }

  ngOnInit(): void {
  }

  logout() {
    localStorage.removeItem("accessToken");
    this.route.navigate(['/'])
  }
}
