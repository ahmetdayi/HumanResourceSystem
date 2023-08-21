import {HttpHeaders} from "@angular/common/http";
import {TokenType} from "../model/token-type.enum";

const source: string = "http://localhost:8080";
export const tokenHeader =(tokenType: TokenType): HttpHeaders =>{
  if (tokenType == TokenType.ACCESS){
    console.log(localStorage.getItem("access"))
    return new HttpHeaders({
      'Authorization': `Bearer ${localStorage.getItem(tokenType)}`
    })
  }else{
    return new HttpHeaders({
      'Authorization': `Bearer JWT${localStorage.getItem(tokenType)}`
    })
  }

}
export const Endpoints: any = {

  //<---! Home EndPoints--->
  HOME : "http://localhost:4200/",

  //<---! Search EndPoints--->
  HOME_SEARCH: source + "/homePageSearch/", //! Get String query parameters HomeSearchResponse
  APPLY_PAGE_SEARCH: source + "/applyPageSearch/", //! Get String query parameters ApplySearchResponse

  //<---! Advertisement EndPoints--->
  FIND_ALL_ADVERTISEMENT: source + "/advertisement/all",//! Get no parameters
  CREATE_ADVERTISEMENT: source + "/advertisement/create",//! Post createAdvertisementRequest parameters
  CHANGE_ADVERTISEMENT_STATUE: source + "/advertisement/changeStatue/",//! Put bool statue and String advertisementId parameters
  UPDATE_ADVERTISEMENT: source + "/advertisement/update",//! Put UpdateAdvertisementRequest parameters
  FIND_BY_ADVERTISEMENT_ID : source + "/advertisement/findById/",//! Get String id parameters

  //<---! Apply Advertisement EndPoints--->
  CREATE_APPLY_ADVERTISEMENT: source + "/applyAdvertisement/create",//! Post CreateApplyAdvertisementRequest parameters
  FIND_BY_APPLY_ADVERTISEMENT_ID: source + "/applyAdvertisement/byApplyAdvertisementId/",//! Get String applyAdvertisementId parameters
  FIND_BY_JOBSEEKER_ID: source + "/applyAdvertisement/byJobSeekerId/",//! Get String jobSeekerId parameters
  FIND_APPLY_ADVERTISEMENT_BY_ADVERTISEMENT_ID: source + "/applyAdvertisement/byAdvertisementId/",//! Get String jobSeekerId parameters
  FIND_ALL_APPLY_ADVERTISEMENT : source + "/applyAdvertisement/all",//! Get mo parameters
  UPDATE_APPLY_ADVERTISEMENT_STATUE : source + "/applyAdvertisement/changeStatue/",//! Put String statue and String applyAdvertisementId parameters
  FILTER_APPLY_ADVERTISEMENT_BY_STATUE : source + "/applyAdvertisement/filter/",//! Get String statue parameters
  SORTED_BY_MATCH_COUNT : source + "/applyAdvertisement/sortedMatch",//! Get no parameters

  //<---! BlackList EndPoints--->
  CREATE_BLACK_LIST : source + "/blackList/create",//! Post CreateBlackListRequest parameters
  FIND_ALL_BLACK_LIST : source + "/blackList/all",//! Get no parameters
  DELETE_BLACK_LIST : source + "/blackList/delete/",//! Delete String blackListId
  FIND_BY_BLACK_LIST_ID: source + "/blackList/byBlackListId/",//! Get String blackListId
  FIND_BLACK_LIST_BY_JOBSEEKER_ID: source + "/blackList/byJobSeekerId/",//! Get String jobSeekerId

  //<---! JobSeeker EndPoints--->
  GET_PROFILE : source + "/profile/oauth2",//! Get no parameters
  FIND_ALL_JOBSEEKER : source + "/jobSeeker/all",//! Get no parameters
  FIND_JOBSEEKER_BY_ID : source + "/jobSeeker/byId/",//! Get String jobSeekerId parameters
  UPDATE_JOBSEEKER : source + "/jobSeeker/update",//! Put UpdateJobSeekerRequest parameters
  ADD_PERSONAL_SKILL_IN_JOBSEEKER : source + "/jobSeeker/addPersonalSkill",//! Put AddPersonalSkillInJobSeekerRequest parameters

  //<---! Personal Skill EndPoints--->
  CREATE_PERSONAL_SKILL : source + "/personalSkill/create",//!Post CreatePersonalSkillRequest parameters
  DELETE_PERSONAL_SKILL : source +"/personalSkill/delete/",//! Delete String personalSkillId parameters
  FIND_ALL_PERSONAL_SKILL : source + "/personalSkill/all",//! Get no parameters

  //<---! Auth EndPoints--->
  GET_ACCESS_TOKEN : source + "/access", //! Get
  LOGIN_IN_FORM: source + "/login"//! Post LoginRequest parameters



}
