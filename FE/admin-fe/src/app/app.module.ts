import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AuthService} from './services/auth.service';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { PendingCertificatesComponent } from './components/certificates/pending-certificates/pending-certificates.component';
import { AllCertificatesComponent } from './components/certificates/all-certificates/all-certificates.component';
import { RevokeCertificateComponent } from './components/certificates/revoke-certificate/revoke-certificate.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';
import { CertificateComponent } from './components/certificate/certificate.component';
import {MatDialog, MatDialogModule} from "@angular/material/dialog";
import { SubjectsInfoComponent } from './components/subjects-info/subjects-info.component';
import { ApproveRequestComponent } from './components/approve-request/approve-request.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatRadioModule} from '@angular/material/radio';
import {MatIconModule} from '@angular/material/icon';
import { AllUsersComponent } from './components/users/all-users/all-users.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatPaginatorModule} from '@angular/material/paginator';
import { AddUserComponent } from './components/users/add-user/add-user.component';
import { ProfileComponent } from './components/profile/profile.component';
import {AuthInterceptorService} from './interceptors/auth.interceptor';
import { AdminLogsComponent } from './components/admin-logs/admin-logs.component';
import {MatTableModule} from '@angular/material/table';
import {MatSelectModule} from '@angular/material/select';
import {MatInputModule} from '@angular/material/input';
import { HospitalLogsComponent } from './components/hospital-logs/hospital-logs.component';
import { AlarmComponent } from './components/alarm/alarm.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminPanelComponent,
    NavigationBarComponent,
    PendingCertificatesComponent,
    AllCertificatesComponent,
    RevokeCertificateComponent,
    CertificateComponent,
    SubjectsInfoComponent,
    ApproveRequestComponent,
    AllUsersComponent,
    AddUserComponent,
    ProfileComponent,
    AdminLogsComponent,
    HospitalLogsComponent,
    AlarmComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    MatTabsModule,
    MatCheckboxModule,
    MatPaginatorModule,
    MatRadioModule,
    MatIconModule,
    MatTableModule,
    MatInputModule,
    MatSelectModule
  ],
  providers: [
    AuthService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptorService, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
