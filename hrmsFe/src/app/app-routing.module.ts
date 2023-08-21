import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {LoginComponent} from "./components/login/login.component";
import {AdvertisementComponent} from "./components/advertisement/advertisement.component";
import {
  AdvertisementDetailComponent
} from "./components/advertisement/advertisement-detail/advertisement-detail.component";
import {HomeComponent} from "./components/home/home.component";
import {AccessTokenComponent} from "./components/access-token/access-token.component";

import {ApplyAdvertisementComponent} from "./components/apply-advertisement/apply-advertisement.component";
import {
  ApplyAdvertisementDetailComponent
} from "./components/apply-advertisement/apply-advertisement-detail/apply-advertisement-detail.component";
import {Profile} from "./components/profile/profile";
import {
  ProfileUpdateFormComponent
} from "./components/profile/profile-update-form/profile-update-form.component";

import {BlackListComponent} from "./components/black-list/black-list.component";
import {BlackListDetailComponent} from "./components/black-list/black-list-detail/black-list-detail.component";
import {JobSeekerComponent} from "./components/job-seeker/job-seeker.component";
import {
  AdvertisementUpdateFormComponent
} from "./components/advertisement/advertisement-detail/advertisement-update-form/advertisement-update-form.component";
import {
  CreateAdvertisementComponent
} from "./components/advertisement/create-advertisement/create-advertisement.component";
import {
  CreatePersonalSkillComponent
} from "./components/personal-skill/create-personal-skill/create-personal-skill.component";
import {SearchBarComponent} from "./components/search-bar/search-bar.component";
import {
  PrivateApplyAdvertisementComponent
} from "./components/apply-advertisement/private-apply-advertisement/private-apply-advertisement.component";
import {isHumanResourceGuard} from "./guards/is-humanresource.guard";
import {IsJobSeekerGuard} from "./guards/is-job-seeker.guard";
import {ErrorPageComponent} from "./components/error-page/error-page.component";
import {
  AdvertisementToApplicationComponent
} from "./components/advertisement/advertisement-detail/advertisement-to-application/advertisement-to-application.component";


const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },

  {path: "advertisement",component: AdvertisementComponent},
  {path: "advertisement/createAdvertisement", component: CreateAdvertisementComponent,canActivate:[isHumanResourceGuard]},
  {path: "advertisement/:id", component: AdvertisementDetailComponent},
  {path: "advertisement/:id/update", component: AdvertisementUpdateFormComponent,canActivate:[isHumanResourceGuard]},
  {path: "application/:id/advertisement", component: AdvertisementToApplicationComponent,canActivate:[isHumanResourceGuard]},
  {path: "applyAdvertisement", component: ApplyAdvertisementComponent,canActivate:[isHumanResourceGuard]},
  {path: "applyAdvertisement/:id", component: ApplyAdvertisementDetailComponent},
  {path: "myAdvertisements", component: PrivateApplyAdvertisementComponent,canActivate:[IsJobSeekerGuard]},
  {path: "profile", component: Profile},
  {path: "profile/:id", component: Profile},
  {path: "profile/:id/update", component: ProfileUpdateFormComponent,canActivate:[IsJobSeekerGuard]},
  {path: "jobSeeker", component: JobSeekerComponent,canActivate:[isHumanResourceGuard]},
  {path: "blackList", component: BlackListComponent,canActivate:[isHumanResourceGuard]},
  {path: "blackList/:id", component: BlackListDetailComponent,canActivate:[isHumanResourceGuard]},
  {path: "personalSkill/create", component: CreatePersonalSkillComponent,canActivate:[isHumanResourceGuard]},
  {path: "access", component: AccessTokenComponent},
  {path: "searchBar", component: SearchBarComponent},
  {path: "home", component: HomeComponent},
  {path: "", component: HomeComponent},
  {path: "**", component: ErrorPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
