import {GetHumanResourceEntityResponse} from "./getHumanResourceEntityResponse";
import {GetPersonalSkillResponse} from "./getPersonalSkillResponse";

export interface GetAdvertisementResponse{

  id:string,

  advCode:string,

  title:string,

  jobDescription:string,

  activationTime:Date,

  offDate:Date,

  advertisementStatue:String, // TODO buna da bak

  humanResourceEntity:GetHumanResourceEntityResponse,

  personalSkills: GetPersonalSkillResponse[]
}
