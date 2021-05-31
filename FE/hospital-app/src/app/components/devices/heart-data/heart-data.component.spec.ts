import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HeartDataComponent } from './heart-data.component';

describe('HeartDataComponent', () => {
  let component: HeartDataComponent;
  let fixture: ComponentFixture<HeartDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HeartDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HeartDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
