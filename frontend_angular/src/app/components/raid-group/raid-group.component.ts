import {Component, QueryList, ViewChild, ViewChildren} from '@angular/core';
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
  @ViewChildren(RaiderComponent) raiderComponents: QueryList<RaiderComponent> | undefined;
  mouseoverTarget: RaiderComponent | undefined;
  raiders = 20;
  protected readonly name = name;

  constructor() {
    setInterval(() =>  {
      console.log(this.mouseoverTarget);
      if (this.mouseoverTarget) {
        this.mouseoverTarget.currentHealthPoints += 50;
      }
    }, 100);
    }
  doSomething(raiderComponent: RaiderComponent) {
    raiderComponent.isAlive = true;
    raiderComponent.currentHealthPoints += 150;
  }


}
