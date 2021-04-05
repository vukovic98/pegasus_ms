import { Component, OnInit } from '@angular/core';
import {CertificatesService} from "../../../services/certificates.service";
import Swal from "sweetalert2";
import {MatDialog} from "@angular/material/dialog";
import {CertificateComponent} from "../../certificate/certificate.component";
import {CertificateModel} from "../../../models/certificate.model";

@Component({
  selector: 'app-all-certificates',
  templateUrl: './all-certificates.component.html',
  styleUrls: ['./all-certificates.component.css']
})
export class AllCertificatesComponent implements OnInit {

  constructor(private certService: CertificatesService,
              private dialog: MatDialog
  )  { }

  public certificates = [];
  private newModel: CertificateModel = {
    issuedDate: "",
    issuerCN: "",
    issuerEmail: "",
    issuerID: 0,
    issuerOU: "",
    serialNum: 0,
    subjectCN: "",
    subjectEmail: "",
    subjectID: 0,
    subjectOU: "",
    validToDate: "",
    revokedReason: "",
    revoked: false
  };

  ngOnInit(): void {
    this.certService.getAll().subscribe(
      (certs: any) => {
        this.certificates = certs;
      }
    )

  }

  openCertInfo(serialNum: number) {
    this.certService.getOne(serialNum).subscribe((cert) => {
        this.newModel = cert;
        const dialogRef = this.dialog.open(CertificateComponent, {
        width: '650px',
        height: '550px',
        data: this.newModel
      });
    })

  }
}
