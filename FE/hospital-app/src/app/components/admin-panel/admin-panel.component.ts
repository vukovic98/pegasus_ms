import { Component, OnInit } from '@angular/core';
import {HospitalService} from '../../services/hospital.service';
import {log} from 'util';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {

  public certified: boolean;

  constructor(
    private hospitalService: HospitalService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.hospitalService.isVerified(Number(this.authService.getHospitalId())).subscribe((response) => {
      this.certified = Boolean(response);
      }, error => {
      console.log(error);
    })
  }

}
