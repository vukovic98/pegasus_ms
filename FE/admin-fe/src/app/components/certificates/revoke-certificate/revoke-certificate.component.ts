import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-revoke-certificate',
  templateUrl: './revoke-certificate.component.html',
  styleUrls: ['./revoke-certificate.component.css']
})
export class RevokeCertificateComponent implements OnInit {
  revokeForm = new FormGroup({
  serialNum: new FormControl('', [Validators.required]),
  reason: new FormControl('', Validators.required)
});

  constructor() { }

  ngOnInit(): void {
  }

  revokeCertificate(): any {

  }
}
