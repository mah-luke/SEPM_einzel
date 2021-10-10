import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HorseComponent } from './component/horse/horse.component';
import {CreateHorseComponent} from './component/create-horse/create-horse.component';
import {EditHorseComponent} from './component/edit-horse/edit-horse.component';
import {HorseDetailsComponent} from './component/horse-details/horse-details.component';

const routes: Routes = [
  {path: '', redirectTo: 'horses', pathMatch: 'full'},
  {path: 'horses', component: HorseComponent},
  {path: 'horses/create', component: CreateHorseComponent},
  {path: 'horses/:id/edit', component: EditHorseComponent},
  {path: 'horses/:id/details', component: HorseDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
