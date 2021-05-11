import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AdminPanelComponent} from './components/admin-panel/admin-panel.component';
import {LoginComponent} from './components/login/login.component';
import {CertificateComponent} from './components/certificate/certificate.component';
import {PatientsComponent} from './components/patients/patients/patients.component';
import {PatientDetailsComponent} from "./components/patients/patientDetails/patient-details/patient-details.component";
import {RoleGuard} from "./guards/role.guard";

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'admin-panel', component: AdminPanelComponent },
  { path: 'certificate', component: CertificateComponent },
  { path: 'patient',
    component: PatientsComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles:'ROLE_DOCTOR'}
  },
  { path: 'patient-details',
    component:PatientDetailsComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles:'ROLE_DOCTOR'}}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
