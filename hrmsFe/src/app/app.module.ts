import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from "@angular/common/http";
import { NavbarComponent } from './components/navbar/navbar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import { AppRoutingModule } from './app-routing.module';
import {FlexModule} from "@angular/flex-layout";
import {FooterComponent} from "./components/footer/footer.component";
import { AdvertisementComponent } from './components/advertisement/advertisement.component';
import { LoginComponent } from './components/login/login.component';
import { AdvertisementDetailComponent } from './components/advertisement/advertisement-detail/advertisement-detail.component';
import { HomeComponent } from './components/home/home.component';
import { AccessTokenComponent } from './components/access-token/access-token.component';

import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ApplyAdvertisementComponent } from './components/apply-advertisement/apply-advertisement.component';
import { ApplyAdvertisementDetailComponent } from './components/apply-advertisement/apply-advertisement-detail/apply-advertisement-detail.component';
import { Profile } from './components/profile/profile';
import {
  ProfileUpdateFormComponent
} from './components/profile/profile-update-form/profile-update-form.component';
import { BlackListComponent } from './components/black-list/black-list.component';
import { BlackListDetailComponent } from './components/black-list/black-list-detail/black-list-detail.component';
import { JobSeekerComponent } from './components/job-seeker/job-seeker.component';
import { AdvertisementUpdateFormComponent } from './components/advertisement/advertisement-detail/advertisement-update-form/advertisement-update-form.component';
import { CreateAdvertisementComponent } from './components/advertisement/create-advertisement/create-advertisement.component';
import { CreatePersonalSkillComponent } from './components/personal-skill/create-personal-skill/create-personal-skill.component';
import { SearchBarComponent } from './components/search-bar/search-bar.component';
import { ToastrModule } from 'ngx-toastr';
import { PrivateApplyAdvertisementComponent } from './components/apply-advertisement/private-apply-advertisement/private-apply-advertisement.component';
import { ErrorPageComponent } from './components/error-page/error-page.component';
import { AdvertisementToApplicationComponent } from './components/advertisement/advertisement-detail/advertisement-to-application/advertisement-to-application.component';






@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    AdvertisementComponent,
    LoginComponent,
    AdvertisementDetailComponent,
    HomeComponent,
    AccessTokenComponent,

    ApplyAdvertisementComponent,
    ApplyAdvertisementDetailComponent,
    Profile,
    ProfileUpdateFormComponent,
    BlackListComponent,
    BlackListDetailComponent,
    JobSeekerComponent,
    AdvertisementUpdateFormComponent,
    CreateAdvertisementComponent,
    CreatePersonalSkillComponent,
    SearchBarComponent,
    PrivateApplyAdvertisementComponent,
    ErrorPageComponent,
    AdvertisementToApplicationComponent,





  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    AppRoutingModule,
    FlexModule,
    FormsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot(),


  ],
  providers: [HttpClient,

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
