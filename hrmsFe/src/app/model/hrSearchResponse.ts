import {HomeSearchResponse} from "./homeSearchResponse";
import {ApplySearchResponse} from "./applySearchResponse";
import {GetJobSeekerResponse} from "./getJobSeekerResponse";
import {GetAdvertisementResponse} from "./getAdvertisementResponse";
import {GetApplyAdvertisementResponse} from "./getApplyAdvertisementResponse";

export interface HrSearchResponse{

  jobSeeker: GetJobSeekerResponse[];
  advertisement: GetAdvertisementResponse[];
  applyAdvertisement: GetApplyAdvertisementResponse[];
}
