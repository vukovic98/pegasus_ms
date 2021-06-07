import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HospitalLogsComponent } from './hospital-logs.component';

describe('HospitalLogsComponent', () => {
  let component: HospitalLogsComponent;
  let fixture: ComponentFixture<HospitalLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HospitalLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HospitalLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
