import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuBarComponent } from './component/menu-bar/menu-bar.component';
import { LoginFormComponent } from './component/login-form/login-form.component';
import { InstructorComponent } from './component/instructor/instructor.component';
import { ProductListComponent } from './component/product-list/product-list.component';
import { HttpClientModule} from '@angular/common/http';
import { ProductService } from './services/product.service';
import { registerLocaleData } from '@angular/common';
import localeHu from '@angular/common/locales/hu';
import { FooterComponent } from './component/footer/footer.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { ProductCategoryMenuComponent } from './component/product-category-menu/product-category-menu.component';
import { SearchComponent } from './component/search/search.component';
registerLocaleData(localeHu, 'hu');


@NgModule({
  declarations: [
    AppComponent,
    MenuBarComponent,
    LoginFormComponent,
    InstructorComponent,
    ProductListComponent,
    FooterComponent,
    PageNotFoundComponent,
    ProductCategoryMenuComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [ProductService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
