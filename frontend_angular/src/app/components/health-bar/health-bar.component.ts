import {Component, Input} from '@angular/core';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-health-bar',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './health-bar.component.html',
  styleUrl: './health-bar.component.scss'
})
export class HealthBarComponent {
  @Input() currentHealthPoints: number = 100;
  @Input() maxHealthPoints: number = 100;
  @Input() isAlive: boolean = true;
}
