import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RaidGroupComponent } from './raid-group.component';

describe('RaidGroupComponent', () => {
  let component: RaidGroupComponent;
  let fixture: ComponentFixture<RaidGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RaidGroupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(RaidGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
