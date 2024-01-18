import {Component, Input} from '@angular/core';
import {HealthBarComponent} from '../health-bar/health-bar.component';
import {NgStyle} from '@angular/common';

@Component({
  selector: 'app-raider',
  standalone: true,
  imports: [
    HealthBarComponent,
    NgStyle
  ],
  templateUrl: './raider.component.html',
  styleUrl: './raider.component.scss'
})
export class RaiderComponent {
  @Input() currentHealthPoints: number = 1000;
  @Input() maxHealthPoints: number = 1000;
  @Input() isAlive: boolean = true;
  @Input() name: string = "";

  constructor() {
    setInterval(() =>  {
      if (this.currentHealthPoints > 0) {
        this.currentHealthPoints = this.currentHealthPoints -10
      }
      else {
        this.isAlive = false;
      }
    }, 100);
  }
}
