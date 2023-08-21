import {GetJobSeekerResponse} from "./getJobSeekerResponse";
import {GetHumanResourceEntityResponse} from "./getHumanResourceEntityResponse";

export interface CreateBlackListResponse{

  id:string,

  description:string,

  jobSeeker:GetJobSeekerResponse

  humanResourceEntity:GetHumanResourceEntityResponse
}
