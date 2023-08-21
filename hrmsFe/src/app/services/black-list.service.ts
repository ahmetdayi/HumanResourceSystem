import {Injectable} from '@angular/core';
import {HttpService} from "./http.service";
import {CreateBlackListRequest} from "../model/requests/createBlackListRequest";
import {Observable} from "rxjs";
import {CreateBlackListResponse} from "../model/createBlackListResponse";
import {Endpoints, tokenHeader} from "../utility/endpoints";
import {GetBlackListResponse} from "../model/getBlackListResponse";
import {TokenType} from "../model/token-type.enum";

@Injectable({
  providedIn: 'root'
})
export class BlackListService {

  jwtHeader:any = tokenHeader(TokenType.JWT);
  constructor(private httpService: HttpService) {
  }

  public createBlackList(request: CreateBlackListRequest): Observable<CreateBlackListResponse> {
    return this.httpService.POST<CreateBlackListResponse>(
      Endpoints.CREATE_BLACK_LIST, request,this.jwtHeader);
  }

  public findAllBlackList(): Observable<GetBlackListResponse[]> {
    return this.httpService.GET<GetBlackListResponse[]>(
      Endpoints.FIND_ALL_BLACK_LIST,this.jwtHeader);
  }
  public findByJobSeekerId(jobSeekerId:string): Observable<GetBlackListResponse> {
    return this.httpService.GET<GetBlackListResponse>(
      Endpoints.FIND_BLACK_LIST_BY_JOBSEEKER_ID + jobSeekerId,this.jwtHeader);
  }
  public findByBlackListId(blackListId: string): Observable<GetBlackListResponse> {
    return this.httpService.GET<GetBlackListResponse>(
      Endpoints.FIND_BY_BLACK_LIST_ID + blackListId,this.jwtHeader );
  }

  public deleteBlackList(id: string): Observable<any> {
    return this.httpService.DELETE(Endpoints.DELETE_BLACK_LIST + id,{},this.jwtHeader);
  }

}
