import { Component } from '@angular/core';
import {BossComponent} from '../../components/boss/boss.component';
import {RaidGroupComponent} from '../../components/raid-group/raid-group.component';
import {CastBarComponent} from '../../components/cast-bar/cast-bar.component';
import {NgIf} from '@angular/common';
import {ManaBarComponent} from '../../components/mana-bar/mana-bar.component';
import {SpellBarComponent} from '../../components/spell-bar/spell-bar.component';

@Component({
  selector: 'app-game-page',
  standalone: true,
  imports: [
    BossComponent,
    RaidGroupComponent,
    CastBarComponent,
    NgIf,
    ManaBarComponent,
    SpellBarComponent
  ],
  templateUrl: './game-page.component.html',
  styleUrl: './game-page.component.scss'
})
export class GamePageComponent {

}
