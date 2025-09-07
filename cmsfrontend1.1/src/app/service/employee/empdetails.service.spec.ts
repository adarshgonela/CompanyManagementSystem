import { TestBed } from '@angular/core/testing';

import { EmpdetailsService } from './empdetails.service';

describe('EmpdetailsService', () => {
  let service: EmpdetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmpdetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
