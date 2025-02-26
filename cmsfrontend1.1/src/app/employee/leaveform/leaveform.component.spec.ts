import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaveformComponent } from './leaveform.component';

describe('LeaveformComponent', () => {
  let component: LeaveformComponent;
  let fixture: ComponentFixture<LeaveformComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LeaveformComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeaveformComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
