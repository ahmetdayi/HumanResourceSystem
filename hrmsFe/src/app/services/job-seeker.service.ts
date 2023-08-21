import {Injectable} from '@angular/core';
import {HttpService} from "./http.service";
import {Observable} from "rxjs";
import {GetJobSeekerResponse} from "../model/getJobSeekerResponse";
import {Endpoints, tokenHeader} from "../utility/endpoints";
import {UpdateJobSeekerRequest} from "../model/requests/updateJobSeekerRequest";
import {AddPersonalSkillInJobSeekerRequest} from "../model/requests/addPersonalSkillInJobSeekerRequest";
import {UpdateJobSeekerResponse} from "../model/updateJobSeekerResponse";
import {TokenType} from "../model/token-type.enum";
import {AuthService} from "./auth.service";

@Injectable({
  providedIn: 'root'
})
export class JobSeekerService {

  jwtHeader:any = tokenHeader(TokenType.JWT);
  accessHeader:any = tokenHeader(TokenType.ACCESS);
  constructor(private httpService: HttpService,private authService: AuthService) {
  }

  public getProfile(): Observable<GetJobSeekerResponse> {
    return this.httpService.GET<GetJobSeekerResponse>(
      Endpoints.GET_PROFILE,this.accessHeader);
  }

  public findAllJobSeeker(): Observable<GetJobSeekerResponse[]> {
    return this.httpService.GET<GetJobSeekerResponse[]>(
      Endpoints.FIND_ALL_JOBSEEKER,this.jwtHeader);
  }

  public findJobSeekerById(id: string): Observable<GetJobSeekerResponse> {
    return this.httpService.GET<GetJobSeekerResponse>(
      Endpoints.FIND_JOBSEEKER_BY_ID + id,this.jwtHeader);
  }

  public updateJobSeeker(request: UpdateJobSeekerRequest): Observable<UpdateJobSeekerResponse> {
    return this.httpService.PUT<GetJobSeekerResponse>(
      Endpoints.UPDATE_JOBSEEKER, request,this.accessHeader);
  }

  public addPersonalSkillInJobSeeker(request: AddPersonalSkillInJobSeekerRequest): Observable<GetJobSeekerResponse> {
    return this.httpService.PUT<GetJobSeekerResponse>(
      Endpoints.ADD_PERSONAL_SKILL_IN_JOBSEEKER, request,this.accessHeader);
  }
}
