import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AuthService} from './services/auth.service';
import {AdminPanelComponent} from './components/admin-panel/admin-panel.component';
import {PendingCertificatesComponent} from './components/certificates/pending-certificates/pending-certificates.component';
import {AllCertificatesComponent} from './components/certificates/all-certificates/all-certificates.component';
import {RevokeCertificateComponent} from './components/certificates/revoke-certificate/revoke-certificate.component';
import {CertificateComponent} from "./components/certificate/certificate.component";

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'admin-panel', component: AdminPanelComponent },
  { path: 'pending-certificates', component: PendingCertificatesComponent },
  { path: 'all-certificates', component: AllCertificatesComponent },
  { path: 'revoke-certificate', component: RevokeCertificateComponent },
  { path: 'certificate', component: CertificateComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
