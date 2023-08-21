import {GetJobSeekerResponse} from "./getJobSeekerResponse";
import {GetAdvertisementResponse} from "./getAdvertisementResponse";

export interface CreateApplyAdvertisementResponse{

  id:string,

  applyDate:Date,

  applyAdvertisementStatue:string,// TODO bu string mı olacak dene

  jobSeeker:GetJobSeekerResponse,

  advertisement:GetAdvertisementResponse
}
