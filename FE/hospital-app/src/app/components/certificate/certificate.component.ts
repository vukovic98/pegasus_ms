import { Component, OnInit } from '@angular/core';
import {HospitalService} from '../../services/hospital.service';
import {AuthService} from '../../services/auth.service';
import {CertificateService} from '../../services/certificate.service';

@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {

  public certified: boolean;

  constructor(
    private hospitalService: HospitalService,
    private authService: AuthService,
    private certService: CertificateService
  ) { }

  ngOnInit(): void {
    this.hospitalService.isVerified(Number(this.authService.getHospitalId())).subscribe((response) => {
      this.certified = Boolean(response);
    }, error => {
      console.log(error);
    })
  }

  requestCertificate() {
    this.certService.requestCertificate().subscribe((response) => {
      this.certService.sendRequestToAdmin(response).subscribe((response) => {
        console.log("ok");
      })
    }, error => {
      console.log(error);
    })
  }
}
