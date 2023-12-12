import { Routes } from '@angular/router';
import {GamePageComponent} from './pages/game-page/game-page.component';
import {AboutPageComponent} from './pages/about-page/about-page.component';
import {HomePageComponent} from './pages/home-page/home-page.component';

export const routes: Routes = [
  {
    path: 'game',
    component: GamePageComponent,
  },
  {
    path: 'about',
    component: AboutPageComponent,
  },
  {
    path: 'home',
    component: HomePageComponent,
  },
  {
    path: '',
    redirectTo: 'game',
    pathMatch: 'full',
  }
];
