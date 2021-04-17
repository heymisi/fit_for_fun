import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuBarComponent } from './component/menu-bar/menu-bar.component';
import { ProductListComponent } from './component/product-list/product-list.component';
import { HttpClientModule } from '@angular/common/http';
import { ProductService } from './services/product.service';
import { registerLocaleData } from '@angular/common';
import { FooterComponent } from './component/footer/footer.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { ProductCategoryMenuComponent } from './component/product-category-menu/product-category-menu.component';
import { SearchComponent } from './component/search/search.component';
import { ProductDetailsComponent } from './component/product-details/product-details.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { CartStatusComponent } from './component/cart-status/cart-status.component';
import { CartDetailsComponent } from './component/cart-details/cart-details.component';
import { CheckoutComponent } from './component/checkout/checkout.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import {MatDividerModule} from '@angular/material/divider';
import {MatButtonToggleModule} from '@angular/material/button-toggle';
import { FormsModule } from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatTooltipModule} from '@angular/material/tooltip';


import localeHu from '@angular/common/locales/hu';
import { CommentListComponent } from './component/comments/comment-list/comment-list.component';
import { LoginComponent } from './component/login/login.component';
import { RegistrationComponent } from './component/registration/registration.component';
import { ShopHeaderComponent } from './component/shop-header/shop-header.component';
import {MatTabsModule} from '@angular/material/tabs';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MatDialogModule} from '@angular/material/dialog';
import { SuccesRegistrationComponent } from './component/dialogs/succes-registration/succes-registration.component';
import { PasswordResetComponent } from './component/password-reset/password-reset.component';
import { PasswordResetDialogComponent } from './component/dialogs/password-reset-dialog/password-reset-dialog.component';
import { LoginErrorDialogComponent } from './component/dialogs/login-error-dialog/login-error-dialog.component';
import {} from '@angular/material/divider';
import {MatExpansionModule} from '@angular/material/expansion';
import { CommentAddedDialogComponent } from './component/dialogs/comment-added-dialog/comment-added-dialog.component';
import {MatRadioModule} from '@angular/material/radio';
import { FacilityListComponent } from './component/facility-list/facility-list.component';
import { FacilityDetailsComponent } from './component/facility-details/facility-details.component';
import { OverlayModule } from '@angular/cdk/overlay';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { InstructorListComponent } from './component/instructor-list/instructor-list.component';
import { InstructorDetailsComponent } from './component/instructor-details/instructor-details.component';
import { AboutUsComponent } from './component/about-us/about-us.component';
import { ContactUsComponent } from './component/contact-us/contact-us.component';
import { ContactUsDialogComponent } from './component/dialogs/contact-us-dialog/contact-us-dialog.component';
import { InstructorScheduleComponent } from './component/instructor-schedule/instructor-schedule.component';
import { AdminIndexComponent } from './component/admin/admin-index/admin-index.component';
import { AdminProductsComponent } from './component/admin/admin-products/admin-products.component';
import { AdminInstructorsComponent } from './component/admin/admin-instructors/admin-instructors.component';
import { AdminFacilitesComponent } from './component/admin/admin-facilites/admin-facilites.component';
import { AdminUsersComponent } from './component/admin/admin-users/admin-users.component';
import {TableModule} from 'primeng/table';
import {ButtonModule} from 'primeng/button';
import {PaginatorModule} from 'primeng/paginator';
import { InputTextModule } from 'primeng/inputtext';
import {ToastModule} from 'primeng/toast';
import {CalendarModule} from 'primeng/calendar';
import {SliderModule} from 'primeng/slider';
import {MultiSelectModule} from 'primeng/multiselect';
import {ContextMenuModule} from 'primeng/contextmenu';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import {ProgressBarModule} from 'primeng/progressbar';
import {ToolbarModule} from 'primeng/toolbar';
import {FileUploadModule} from 'primeng/fileupload';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import { ConfirmationService, MessageService } from 'primeng/api';
import {RatingModule} from 'primeng/rating';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputNumberModule } from 'primeng/inputnumber';
import {RadioButtonModule} from 'primeng/radiobutton';

registerLocaleData(localeHu, 'hu');


@NgModule({
  declarations: [
    AppComponent,
    MenuBarComponent,
    ProductListComponent,
    FooterComponent,
    PageNotFoundComponent,
    ProductCategoryMenuComponent,
    SearchComponent,
    ProductDetailsComponent,
    CartStatusComponent,
    CartDetailsComponent,
    CheckoutComponent,
    CommentListComponent,
    LoginComponent,
    RegistrationComponent,
    ShopHeaderComponent,
    SuccesRegistrationComponent,
    PasswordResetComponent,
    PasswordResetDialogComponent,
    LoginErrorDialogComponent,
    CommentAddedDialogComponent,
    FacilityListComponent,
    FacilityDetailsComponent,
    InstructorListComponent,
    InstructorDetailsComponent,
    AboutUsComponent,
    ContactUsComponent,
    ContactUsDialogComponent,
    InstructorScheduleComponent,
    AdminIndexComponent,
    AdminProductsComponent,
    AdminInstructorsComponent,
    AdminFacilitesComponent,
    AdminUsersComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatButtonToggleModule,
    FormsModule,
    MatTabsModule,
    MatInputModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatDialogModule,
    MatCardModule,
    MatExpansionModule,
    MatCheckboxModule,
    MatRadioModule,
    MatTooltipModule,
    OverlayModule,
    TableModule,
    ButtonModule,
    PaginatorModule,
    ToastModule,
    CalendarModule,
		SliderModule,
		DialogModule,
		MultiSelectModule,
		ContextMenuModule,
		DropdownModule,
    InputTextModule,
    ProgressBarModule,
    ToolbarModule,
    FileUploadModule,
    ConfirmDialogModule,
    InputTextareaModule,
    RatingModule,
    InputNumberModule,
    RadioButtonModule,

  ],
  providers: [ProductService, MessageService, ConfirmationService
  ],
  bootstrap: [AppComponent],

})
export class AppModule { }
