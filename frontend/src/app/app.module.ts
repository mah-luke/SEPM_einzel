import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './component/header/header.component';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';
import {HorseComponent} from './component/horse/horse.component';
import {CreateHorseComponent} from './component/create-horse/create-horse.component';
import { ShowErrorComponent } from './component/show-error/show-error.component';
import { EditHorseComponent } from './component/edit-horse/edit-horse.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HorseComponent,
    CreateHorseComponent,
    ShowErrorComponent,
    EditHorseComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
