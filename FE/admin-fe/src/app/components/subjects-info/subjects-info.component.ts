import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CertificateModel} from "../../models/certificate.model";
import {CertificateRequestModel} from "../../models/certificate-request.model";

@Component({
  selector: 'app-subjects-info',
  templateUrl: './subjects-info.component.html',
  styleUrls: ['./subjects-info.component.css']
})
export class SubjectsInfoComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<SubjectsInfoComponent>,
              @Inject(MAT_DIALOG_DATA) public data: CertificateRequestModel) { }
  ngOnInit(): void {
  }

}
