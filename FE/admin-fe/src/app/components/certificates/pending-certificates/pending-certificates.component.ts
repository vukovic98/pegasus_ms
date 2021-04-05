import { Component, OnInit } from '@angular/core';
import {CertificateRequestModel} from "../../../models/certificate-request.model";
import {MatDialog} from "@angular/material/dialog";
import {CertificatesService} from "../../../services/certificates.service";
import {SubjectsInfoComponent} from "../../subjects-info/subjects-info.component";

@Component({
  selector: 'app-pending-certificates',
  templateUrl: './pending-certificates.component.html',
  styleUrls: ['./pending-certificates.component.css']
})
export class PendingCertificatesComponent implements OnInit {

  constructor(private dialog: MatDialog, private certService: CertificatesService) { }
  public requests = [];


  ngOnInit(): void {
    this.certService.getAllCertificateRequests().subscribe(
      (reqs: any) => {
        this.requests = reqs;
      }
    )
  }

  openSubjectsInfo(req: CertificateRequestModel){
    this.dialog.open(SubjectsInfoComponent, {
      width: '500px',
      height: '450px',
      data: req
    });
  }

  accept(id: number) {
    this.certService.approveForCertificate(id).subscribe((response) => {
      this.certService.getAllCertificateRequests().subscribe(
        (reqs: any) => {
          this.requests = reqs;
        }
      )
    }, error => {
      console.log(error);
    })
  }

  deny(id: number) {
    this.certService.denyCertificateRequest(id).subscribe((response) => {
      this.certService.getAllCertificateRequests().subscribe(
        (reqs: any) => {
          this.requests = reqs;
        }
      )
    }, error => {
      console.log(error);
    })
  }
}
