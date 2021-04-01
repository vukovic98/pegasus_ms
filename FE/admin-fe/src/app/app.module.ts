import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AuthService} from './services/auth.service';
import { AdminPanelComponent } from './components/admin-panel/admin-panel.component';
import { NavigationBarComponent } from './components/navigation-bar/navigation-bar.component';
import { PendingCertificatesComponent } from './components/certificates/pending-certificates/pending-certificates.component';
import { AllCertificatesComponent } from './components/certificates/all-certificates/all-certificates.component';
import { RevokeCertificateComponent } from './components/certificates/revoke-certificate/revoke-certificate.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from '@angular/material/button';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminPanelComponent,
    NavigationBarComponent,
    PendingCertificatesComponent,
    AllCertificatesComponent,
    RevokeCertificateComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule
  ],
  providers: [
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
