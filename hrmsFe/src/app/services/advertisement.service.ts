import {Injectable} from '@angular/core';
import {HttpService} from "./http.service";
import {Observable} from "rxjs";
import {GetAdvertisementResponse} from "../model/getAdvertisementResponse";
import {Endpoints, tokenHeader} from "../utility/endpoints";
import {CreateAdvertisementRequest} from "../model/requests/createAdvertisementRequest";
import {CreateAdvertisementResponse} from "../model/createAdvertisementResponse";
import {UpdateAdvertisementRequest} from "../model/requests/updateAdvertisementRequest";
import {UpdateAdvertisementResponse} from "../model/updateAdvertisementResponse";
import {TokenType} from "../model/token-type.enum";

@Injectable({
  providedIn: 'root'
})
export class AdvertisementService {

  jwtHeader:any = tokenHeader(TokenType.JWT);

  constructor(private httpService: HttpService) {
  }

  public findAllAdvertisement(): Observable<GetAdvertisementResponse[]> {
    return this.httpService.GET<GetAdvertisementResponse[]>(Endpoints.FIND_ALL_ADVERTISEMENT);
  }

  public createAdvertisement(request: CreateAdvertisementRequest): Observable<CreateAdvertisementResponse> {
    return this.httpService.POST<CreateAdvertisementResponse>(Endpoints.CREATE_ADVERTISEMENT, request,this.jwtHeader);
  }

  public changeAdvertisementStatue(statue: boolean, advertisementId: string): Observable<any> {
    return this.httpService.PUT(Endpoints.CHANGE_ADVERTISEMENT_STATUE + statue + "/" + advertisementId,{},this.jwtHeader);
  }

  public updateAdvertisement(request: UpdateAdvertisementRequest): Observable<UpdateAdvertisementResponse> {
    return this.httpService.PUT(Endpoints.UPDATE_ADVERTISEMENT, request,this.jwtHeader);
  }

  public findById(advertisementId:string):Observable<GetAdvertisementResponse>{
    return this.httpService.GET(Endpoints.FIND_BY_ADVERTISEMENT_ID + advertisementId);
  }
}
