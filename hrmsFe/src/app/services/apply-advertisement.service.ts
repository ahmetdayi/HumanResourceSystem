import {Injectable} from '@angular/core';
import {HttpService} from "./http.service";
import {CreateApplyAdvertisementRequest} from "../model/requests/createApplyAdvertisementRequest";
import {Observable} from "rxjs";
import {CreateApplyAdvertisementResponse} from "../model/createApplyAdvertisementResponse";
import {Endpoints, tokenHeader} from "../utility/endpoints";
import {GetApplyAdvertisementResponse} from "../model/getApplyAdvertisementResponse";
import {AuthService} from "./auth.service";
import {TokenType} from "../model/token-type.enum";

@Injectable({
  providedIn: 'root'
})
export class ApplyAdvertisementService {

  jwtHeader:any = tokenHeader(TokenType.JWT);
  accessHeader:any = tokenHeader(TokenType.ACCESS);
  constructor(private httpService: HttpService,private authService: AuthService) {
    console.log()
  }

  public createApplyAdvertisement(request: CreateApplyAdvertisementRequest): Observable<CreateApplyAdvertisementResponse> {
    return this.httpService.POST<CreateApplyAdvertisementResponse>(
      Endpoints.CREATE_APPLY_ADVERTISEMENT, request,this.accessHeader);
  }

  public findByAdvertisementId(advertisementId: string): Observable<GetApplyAdvertisementResponse[]> {
    return this.httpService.GET<GetApplyAdvertisementResponse[]>(
      Endpoints.FIND_APPLY_ADVERTISEMENT_BY_ADVERTISEMENT_ID + advertisementId,this.jwtHeader);
  }
  public findByApplyAdvertisementId(applyAdvertisementId: string): Observable<GetApplyAdvertisementResponse> {
    return this.httpService.GET<GetApplyAdvertisementResponse>(
      Endpoints.FIND_BY_APPLY_ADVERTISEMENT_ID + applyAdvertisementId,
      this.accessHeader.get("Authorization") != "Bearer null" ? this.accessHeader : this.jwtHeader );
  }

  public findByJobSeekerId(jobSeekerId: string): Observable<GetApplyAdvertisementResponse[]> {
    return this.httpService.GET<GetApplyAdvertisementResponse[]>(
      Endpoints.FIND_BY_JOBSEEKER_ID + jobSeekerId,this.accessHeader);
  }

  public findAllApplyAdvertisement(): Observable<GetApplyAdvertisementResponse[]> {
    return this.httpService.GET<GetApplyAdvertisementResponse[]>(
      Endpoints.FIND_ALL_APPLY_ADVERTISEMENT,tokenHeader(TokenType.JWT));
  }

  public updateApplyAdvertisementStatue(statue: string, applyAdvertisementId: string): Observable<string> {
    return this.httpService.PUT<string>(
      Endpoints.
        UPDATE_APPLY_ADVERTISEMENT_STATUE + statue + "/" + applyAdvertisementId,{},this.jwtHeader);
  }

  public filterApplyAdvertisementByStatue(statue: string): Observable<GetApplyAdvertisementResponse[]> {
    return this.httpService.GET<GetApplyAdvertisementResponse[]>(
      Endpoints.FILTER_APPLY_ADVERTISEMENT_BY_STATUE + statue,this.jwtHeader);
  }

  public sortedApplyAdvertisementByMatchCount(): Observable<GetApplyAdvertisementResponse[]> {
    return this.httpService.GET<GetApplyAdvertisementResponse[]>(
      Endpoints.SORTED_BY_MATCH_COUNT,this.jwtHeader);
  }

}
