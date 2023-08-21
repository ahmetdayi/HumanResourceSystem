import {Injectable} from '@angular/core';
import {HttpService} from "./http.service";
import {CreatePersonalSkillRequest} from "../model/requests/createPersonalSkillRequest";
import {Observable} from "rxjs";
import {CreatePersonalSkillResponse} from "../model/createPersonalSkillResponse";
import {Endpoints, tokenHeader} from "../utility/endpoints";
import {GetPersonalSkillResponse} from "../model/getPersonalSkillResponse";
import {TokenType} from "../model/token-type.enum";

@Injectable({
  providedIn: 'root'
})
export class PersonalSkillService {

  accessHeader:any = tokenHeader(TokenType.ACCESS);
  jwtHeader: any = tokenHeader(TokenType.JWT);
  constructor(private httpService: HttpService) {
  }

  public createPersonalSkill(request: CreatePersonalSkillRequest): Observable<CreatePersonalSkillResponse> {
    return this.httpService.POST<CreatePersonalSkillResponse>(
      Endpoints.CREATE_PERSONAL_SKILL, request,this.jwtHeader);
  }

  public deletePersonalSkill(id: string): Observable<any> {
    return this.httpService.DELETE(
      Endpoints.DELETE_PERSONAL_SKILL + id,{},this.jwtHeader);
  }

  public findAllPersonalSkill(): Observable<GetPersonalSkillResponse[]>{
    return this.httpService.GET<GetPersonalSkillResponse[]>(
      Endpoints.FIND_ALL_PERSONAL_SKILL,this.accessHeader.get("Authorization") != "Bearer null" ? this.accessHeader : this.jwtHeader );
  }
}
