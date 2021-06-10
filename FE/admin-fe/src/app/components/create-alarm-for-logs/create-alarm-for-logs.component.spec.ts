import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAlarmForLogsComponent } from './create-alarm-for-logs.component';

describe('CreateAlarmForLogsComponent', () => {
  let component: CreateAlarmForLogsComponent;
  let fixture: ComponentFixture<CreateAlarmForLogsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAlarmForLogsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAlarmForLogsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
