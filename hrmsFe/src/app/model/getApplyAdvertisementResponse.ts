import {GetJobSeekerResponse} from "./getJobSeekerResponse";
import {GetAdvertisementResponse} from "./getAdvertisementResponse";

export interface GetApplyAdvertisementResponse {

  id: string,

  applyDate: Date,

  applyAdvertisementStatue: string,

  jobSeeker: GetJobSeekerResponse,

  advertisement: GetAdvertisementResponse
}
