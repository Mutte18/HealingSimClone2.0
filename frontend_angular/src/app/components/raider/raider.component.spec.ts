import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaiderComponent } from './raider.component';

describe('RaiderComponent', () => {
  let component: RaiderComponent;
  let fixture: ComponentFixture<RaiderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RaiderComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RaiderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
