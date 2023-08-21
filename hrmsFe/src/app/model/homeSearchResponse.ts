import {GetJobSeekerResponse} from "./getJobSeekerResponse";
import {GetAdvertisementResponse} from "./getAdvertisementResponse";

export interface HomeSearchResponse {
  jobSeeker: GetJobSeekerResponse[],
  advertisement: GetAdvertisementResponse[]
}
