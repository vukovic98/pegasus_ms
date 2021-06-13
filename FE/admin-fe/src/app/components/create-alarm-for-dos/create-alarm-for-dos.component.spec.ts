import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateAlarmForDosComponent } from './create-alarm-for-dos.component';

describe('CreateAlarmForDosComponent', () => {
  let component: CreateAlarmForDosComponent;
  let fixture: ComponentFixture<CreateAlarmForDosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateAlarmForDosComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateAlarmForDosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
