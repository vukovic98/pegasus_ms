import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AdminPanelComponent} from './components/admin-panel/admin-panel.component';
import {NavigationBarComponent} from './components/navigation-bar/navigation-bar.component';
import {LoginComponent} from './components/login/login.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {MatCardModule} from '@angular/material/card';
import {MatButtonModule} from '@angular/material/button';
import {AuthService} from './services/auth.service';
import { CertificateComponent } from './components/certificate/certificate.component';
import {MatIconModule} from '@angular/material/icon';
import { PatientsComponent } from './components/patients/patients/patients.component';
import { PatientDetailsComponent } from './components/patients/patientDetails/patient-details/patient-details.component';
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import { AlarmComponent } from './components/alarm/alarm.component';
import {MatTableModule} from '@angular/material/table';
import { AlarmsComponent } from './components/alarms/alarms.component';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { BloodDataComponent } from './components/devices/blood-data/blood-data.component';
import { HeartDataComponent } from './components/devices/heart-data/heart-data.component';
import { NeuroDataComponent } from './components/devices/neuro-data/neuro-data.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminPanelComponent,
    NavigationBarComponent,
    CertificateComponent,
    PatientsComponent,
    PatientDetailsComponent,
    AlarmComponent,
    AlarmsComponent,
    BloodDataComponent,
    HeartDataComponent,
    NeuroDataComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule,
    MatTableModule,
    MatPaginatorModule,
    MatDialogModule,
    FormsModule
  ],
  providers: [
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
