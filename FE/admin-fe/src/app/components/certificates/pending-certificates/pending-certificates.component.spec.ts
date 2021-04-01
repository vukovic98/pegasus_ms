import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PendingCertificatesComponent } from './pending-certificates.component';

describe('PendingCertificatesComponent', () => {
  let component: PendingCertificatesComponent;
  let fixture: ComponentFixture<PendingCertificatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PendingCertificatesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PendingCertificatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
