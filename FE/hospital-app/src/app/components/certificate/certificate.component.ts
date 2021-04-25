import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth.service';
import {CertificateService} from '../../services/certificate.service';

@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {

  public certified: boolean = true;
  public requested: boolean = true;

  constructor(
    private authService: AuthService,
    private certService: CertificateService
  ) { }

  ngOnInit(): void {
  }

  requestCertificate() {
    this.requested = true;
    this.certService.requestCertificate().subscribe((response) => {
      this.certService.sendRequestToAdmin(response).subscribe((response) => {
        console.log("ok");
      })
    }, error => {
      console.log(error);
    })
  }
}
