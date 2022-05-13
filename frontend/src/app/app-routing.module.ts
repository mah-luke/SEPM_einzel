import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HorseComponent } from './component/horse/horse.component';
import {CreateHorseComponent} from './component/create-horse/create-horse.component';
import {EditHorseComponent} from './component/edit-horse/edit-horse.component';
import {HorseDetailsComponent} from './component/horse-details/horse-details.component';
import {CreateFoodComponent} from './component/create-food/create-food.component';
import {FoodComponent} from './component/food/food.component';

const routes: Routes = [
  {path: '', redirectTo: 'horses', pathMatch: 'full'},
  {path: 'horses', component: HorseComponent},
  {path: 'horses/create', component: CreateHorseComponent},
  {path: 'horses/:id/edit', component: EditHorseComponent},
  {path: 'horses/:id/details', component: HorseDetailsComponent},
  {path: 'food/create', component: CreateFoodComponent},
  {path: 'food', component: FoodComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
