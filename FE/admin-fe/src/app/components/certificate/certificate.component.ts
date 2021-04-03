import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {CertificateModel} from "../../models/certificate.model";

@Component({
  selector: 'app-certificate',
  templateUrl: './certificate.component.html',
  styleUrls: ['./certificate.component.css']
})
export class CertificateComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<CertificateComponent>,
              @Inject(MAT_DIALOG_DATA) public data: CertificateModel) { }

  ngOnInit(): void {
  }

}
