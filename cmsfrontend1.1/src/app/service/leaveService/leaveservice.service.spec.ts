import { TestBed } from '@angular/core/testing';

import { LeaveserviceService } from './leaveservice.service';

describe('LeaveserviceService', () => {
  let service: LeaveserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeaveserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
