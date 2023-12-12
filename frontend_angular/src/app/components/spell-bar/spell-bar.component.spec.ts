import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpellBarComponent } from './spell-bar.component';

describe('SpellBarComponent', () => {
  let component: SpellBarComponent;
  let fixture: ComponentFixture<SpellBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpellBarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SpellBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
