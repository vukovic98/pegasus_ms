import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {CertificateRequestModel} from '../../models/certificate-request.model';
import {CertificatesService} from '../../services/certificates.service';

@Component({
  selector: 'app-approve-request',
  templateUrl: './approve-request.component.html',
  styleUrls: ['./approve-request.component.css']
})
export class ApproveRequestComponent implements OnInit {

  public selectedExtensions: string = 'Certificate Authority';

  constructor(public dialogRef: MatDialogRef<ApproveRequestComponent>,
              @Inject(MAT_DIALOG_DATA) public data: CertificateRequestModel,
              private certService: CertificatesService) { }

  ngOnInit(): void {
  }

  checked(i: string) {
    this.selectedExtensions = i;
  }

  issueCert() {
    this.certService.approveForCertificate(this.data.id, this.selectedExtensions).subscribe((response) => {
      this.dialogRef.close();
    }, error => {
      console.log(error);
      this.dialogRef.close();
    })
  }
}
