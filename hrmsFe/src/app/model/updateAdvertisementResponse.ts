import {GetPersonalSkillResponse} from "./getPersonalSkillResponse";
import {GetHumanResourceEntityResponse} from "./getHumanResourceEntityResponse";

export interface UpdateAdvertisementResponse{
  id:string,

  advCode:string,

  advertisementStatue:string,

  title:string,

  activationTime:Date,

  offDate:Date,

  jobDescription:string,

  personalSkills: GetPersonalSkillResponse[],

  humanResourceEntity:GetHumanResourceEntityResponse
}
