import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetailsfetchComponent } from './detailsfetch.component';

describe('DetailsfetchComponent', () => {
  let component: DetailsfetchComponent;
  let fixture: ComponentFixture<DetailsfetchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DetailsfetchComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DetailsfetchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
