import { InstructorComponent } from './component/instructor/instructor.component';
import { LoginFormComponent } from './component/login-form/login-form.component';
import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { ProductListComponent } from './component/product-list/product-list.component';

const routes: Routes = [
  {
    path: "login",
    component: LoginFormComponent,
  }, 
  {
    path: "search/:keyword",
    component: ProductListComponent,
  },
  {
    path: "category/:id",
    component: ProductListComponent,
  },
  {
    path: "category",
    component: ProductListComponent,
  },
  {
    path: "products",
    component: ProductListComponent,
  },
  {
    path: "",
    redirectTo: '/products',
    pathMatch: 'full'
  },
  {
    path: '**',
    component: PageNotFoundComponent,
    pathMatch: 'full'
  },

]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
