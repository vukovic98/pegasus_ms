import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NeuroDataComponent } from './neuro-data.component';

describe('NeuroDataComponent', () => {
  let component: NeuroDataComponent;
  let fixture: ComponentFixture<NeuroDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NeuroDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NeuroDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
