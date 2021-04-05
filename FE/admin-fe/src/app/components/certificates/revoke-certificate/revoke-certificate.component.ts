import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { CertificatesService } from 'src/app/services/certificates.service';

@Component({
  selector: 'app-revoke-certificate',
  templateUrl: './revoke-certificate.component.html',
  styleUrls: ['./revoke-certificate.component.css']
})
export class RevokeCertificateComponent implements OnInit {
  reasons = [
    {id: 1, reason: "The private key linked to this certificate has been compromised."},
    {id: 2, reason: "The certification authority linked to this certificate has been compromised."},
    {id: 3, reason: "The information on this certificate has changed."},
    {id: 4, reason: "The user has been issued a replacement certificate."},
    {id: 5, reason: "The entity using this certificate is no longer operational."},
    {id: 6, reason: "The certificate holder has requested the revoking."},
    {id: 7, reason: "The certificate has been temporarily revoked."}
  ];
  selectedValue = null;
  SNvalue = "";
  revokeForm = new FormGroup({
  serialNum: new FormControl('', [Validators.required]),
  reason: new FormControl('', Validators.required)
});

  constructor(private certService: CertificatesService, private route: ActivatedRoute) {
    
   }

  ngOnInit(): void {
    this.SNvalue = this.route.snapshot.paramMap.get('SN');
  }

  revokeCertificate(): any {
    console.log(this.selectedValue)
    let request = {serialNumber: Number(this.revokeForm.get('serialNum').value), revokeReason: this.selectedValue}
    this.certService.revokeCertificate(request).subscribe((response) =>{

      console.log("Request sent.")

    })

  }
}
