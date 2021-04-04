import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubjectsInfoComponent } from './subjects-info.component';

describe('SubjectsInfoComponent', () => {
  let component: SubjectsInfoComponent;
  let fixture: ComponentFixture<SubjectsInfoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubjectsInfoComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubjectsInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
