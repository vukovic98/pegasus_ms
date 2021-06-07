import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAlarmsComponent } from './create-alarms.component';

describe('CreateAlarmsComponent', () => {
  let component: CreateAlarmsComponent;
  let fixture: ComponentFixture<CreateAlarmsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAlarmsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAlarmsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
