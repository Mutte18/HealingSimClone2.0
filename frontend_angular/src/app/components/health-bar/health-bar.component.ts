import {Component, Input} from '@angular/core';
import {NgIf, NgStyle} from '@angular/common';

@Component({
  selector: 'app-health-bar',
  standalone: true,
  imports: [
    NgIf,
    NgStyle
  ],
  templateUrl: './health-bar.component.html',
  styleUrl: './health-bar.component.scss'
})
export class HealthBarComponent {
  @Input() currentHealthPoints: number = 0;
  @Input() maxHealthPoints: number = 0;
  @Input() isAlive: boolean = true;

  get healthPercentage(): number {
    return this.isAlive ? (this.currentHealthPoints / this.maxHealthPoints) * 100 : 0;
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
