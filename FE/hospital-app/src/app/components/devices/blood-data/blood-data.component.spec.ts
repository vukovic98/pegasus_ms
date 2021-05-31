import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BloodDataComponent } from './blood-data.component';

describe('BloodDataComponent', () => {
  let component: BloodDataComponent;
  let fixture: ComponentFixture<BloodDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BloodDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BloodDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
