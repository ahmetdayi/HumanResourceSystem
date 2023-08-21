import {GetJobSeekerResponse} from "./getJobSeekerResponse";
import {GetHumanResourceEntityResponse} from "./getHumanResourceEntityResponse";

export interface GetBlackListResponse {

  id: string,

  description: string,

  jobSeeker: GetJobSeekerResponse,

  humanResourceEntity: GetHumanResourceEntityResponse
}
