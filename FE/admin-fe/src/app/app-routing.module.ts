import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AdminPanelComponent} from './components/admin-panel/admin-panel.component';
import {PendingCertificatesComponent} from './components/certificates/pending-certificates/pending-certificates.component';
import {AllCertificatesComponent} from './components/certificates/all-certificates/all-certificates.component';
import {RevokeCertificateComponent} from './components/certificates/revoke-certificate/revoke-certificate.component';
import {CertificateComponent} from "./components/certificate/certificate.component";
import {RoleGuard} from './guards/role.guard';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  {
    path: 'admin-panel',
    component: AdminPanelComponent ,
    canActivate:[RoleGuard],
    data: {acceptRoles: 'ROLE_SUPER_ADMIN'}
  },
  {
    path: 'pending-certificates',
    component: PendingCertificatesComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles: 'ROLE_SUPER_ADMIN'}
  },
  {
    path: 'all-certificates',
    component: AllCertificatesComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles: 'ROLE_SUPER_ADMIN'}
  },
  {
    path: 'revoke-certificate',
    redirectTo:"revoke-certificate/",
    canActivate:[RoleGuard],
    data: {acceptRoles: 'ROLE_SUPER_ADMIN'}
  },
  {
    path: 'revoke-certificate/:SN',
    component: RevokeCertificateComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles: 'ROLE_SUPER_ADMIN'}
  },
  {
    path: 'certificate',
    component: CertificateComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles: 'ROLE_SUPER_ADMIN'}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
