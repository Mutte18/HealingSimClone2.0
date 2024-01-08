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
  @Input() currentHealthPoints: number = 90;
  @Input() maxHealthPoints: number = 100;
  @Input() isAlive: boolean = true;

  get healthPercentage(): number {
    return (this.currentHealthPoints / this.maxHealthPoints) * 100;
  }

  get healthColor(): string {
    const percentage = this.healthPercentage;
    console.log(percentage);

    if (percentage <= 25) {
      return 'red';
    } else if (percentage <= 50) {
      return 'orange';
    } else if (percentage <= 74) {
      return 'yellow';
    } else if (percentage <= 99) {
      return 'lime'
    } else {
      return 'green';
    }
  }
}
