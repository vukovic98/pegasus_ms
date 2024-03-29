import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AdminPanelComponent} from './components/admin-panel/admin-panel.component';
import {LoginComponent} from './components/login/login.component';
import {CertificateComponent} from './components/certificate/certificate.component';
import {PatientsComponent} from './components/patients/patients/patients.component';
import {PatientDetailsComponent} from "./components/patients/patientDetails/patient-details/patient-details.component";
import {RoleGuard} from "./guards/role.guard";
import {AlarmsComponent} from "./components/alarms/alarms.component";
import {BloodDataComponent} from './components/devices/blood-data/blood-data.component';
import {HeartDataComponent} from "./components/devices/heart-data/heart-data.component";
import {NeuroDataComponent} from "./components/devices/neuro-data/neuro-data.component";
import {CreateAlarmsComponent} from "./components/create-alarms/create-alarms.component";
import {ReportsComponent} from './components/reports/reports.component';

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
  { path: 'blood-data',
    component: BloodDataComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles:'ROLE_DOCTOR|ROLE_ADMIN'}
  },
  { path: 'patient-details',
    component:PatientDetailsComponent,
    canActivate:[RoleGuard],
    data: {acceptRoles:'ROLE_DOCTOR'}
    },
  {
    path: 'alarms',
    component: AlarmsComponent,
    canActivate:[RoleGuard],
    data:{acceptRoles: 'ROLE_DOCTOR|ROLE_ADMIN'}
	},
  {
    path: 'create-alarms',
    component: CreateAlarmsComponent,
    canActivate:[RoleGuard],
    data:{acceptRoles: 'ROLE_DOCTOR'}
  },
  {
    path:'heart-data',
    component:HeartDataComponent,
    canActivate:[RoleGuard],
    data:{acceptRoles: 'ROLE_DOCTOR|ROLE_ADMIN'}
  },
  {path:'neuro-data',
    component:NeuroDataComponent,
    canActivate:[RoleGuard],
    data:{acceptRoles: 'ROLE_DOCTOR|ROLE_ADMIN'}
  },
  {path:'reports',
    component:ReportsComponent,
    canActivate:[RoleGuard],
    data:{acceptRoles: 'ROLE_ADMIN'}
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
