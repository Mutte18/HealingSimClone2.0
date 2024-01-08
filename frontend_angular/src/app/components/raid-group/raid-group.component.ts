import { Component } from '@angular/core';
import {RaiderComponent} from '../raider/raider.component';
import {NgForOf} from '@angular/common';

@Component({
  selector: 'app-raid-group',
  standalone: true,
  imports: [
    RaiderComponent,
    NgForOf
  ],
  templateUrl: './raid-group.component.html',
  styleUrl: './raid-group.component.scss'
})
export class RaidGroupComponent {
  raiders = 20;
}
