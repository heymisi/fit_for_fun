import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuBarComponent } from './component/menu-bar/menu-bar.component';
import { LoginFormComponent } from './component/login-form/login-form.component';
import { InstructorComponent } from './component/instructor/instructor.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuBarComponent,
    LoginFormComponent,
    InstructorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
