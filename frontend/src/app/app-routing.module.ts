import { Routes, RouterModule } from '@angular/router';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { ProductListComponent } from './component/product-list/product-list.component';
import { ProductDetailsComponent } from './component/product-details/product-details.component';
import { CartDetailsComponent } from './component/cart-details/cart-details.component';
import { CheckoutComponent } from './component/checkout/checkout.component';
import { LoginComponent } from './component/login/login.component';
import { RegistrationComponent } from './component/registration/registration.component';
import { PasswordResetComponent } from './component/password-reset/password-reset.component';
import { FacilityListComponent } from './component/facility-list/facility-list.component';
import { FacilityDetailsComponent } from './component/facility-details/facility-details.component';
import { InstructorListComponent } from './component/instructor-list/instructor-list.component';
import { InstructorDetailsComponent } from './component/instructor-details/instructor-details.component';
import { NgModule } from '@angular/core';
import { AboutUsComponent } from './component/about-us/about-us.component';
import { ContactUsComponent } from './component/contact-us/contact-us.component';
import { AdminProductsComponent } from './component/admin/admin-products/admin-products.component';
import { AdminUsersComponent } from './component/admin/admin-users/admin-users.component';
import { AdminInstructorsComponent } from './component/admin/admin-instructors/admin-instructors.component';
import { AdminFacilitesComponent } from './component/admin/admin-facilites/admin-facilites.component';
import { AdminTransactionsComponent } from './component/admin/admin-transactions/admin-transactions.component';

const routes: Routes = [
  { path: "checkout", component: CheckoutComponent },
  { path: "cart-details", component: CartDetailsComponent },
  { path: "products/:id", component: ProductDetailsComponent },
  { path: "products/search/:keyword", component: ProductListComponent },
  { path: "category/:id", component: ProductListComponent, },
  { path: "category", component: ProductListComponent, },
  { path: "products", component: ProductListComponent, },
  { path: "instructors", component: InstructorListComponent, },
  { path: "instructors/:id", component: InstructorDetailsComponent, },
  { path: "facilites/:id", component: FacilityDetailsComponent, },
  { path: "facilites", component: FacilityListComponent, },
  { path: "login", component: LoginComponent },
  { path: "register", component: RegistrationComponent },
  { path: "password-reset", component: PasswordResetComponent },
  { path: "home", component: AboutUsComponent },
  { path: "admin/products", component: AdminProductsComponent },
  { path: "admin/users", component: AdminUsersComponent },
  { path: "admin/instructors", component: AdminInstructorsComponent },
  { path: "admin/facilites", component: AdminFacilitesComponent },
  { path: "admin/transactions", component: AdminTransactionsComponent },
  { path: "contact-us", component: ContactUsComponent },
  { path: "", redirectTo: '/admin/users', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent, pathMatch: 'full' },

]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
