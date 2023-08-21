import {Injectable} from '@angular/core';
import {HttpService} from "./http.service";
import {BehaviorSubject, Observable} from "rxjs";
import {HomeSearchResponse} from "../model/homeSearchResponse";
import {Endpoints, tokenHeader} from "../utility/endpoints";
import {ApplySearchResponse} from "../model/applySearchResponse";
import {HrSearchResponse} from "../model/hrSearchResponse";
import {TokenType} from "../model/token-type.enum";

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  // TODO jobSeekerlarıcın yapılmadı
  private homeSearchResponse: HomeSearchResponse;

  applySearchResponse: ApplySearchResponse;

  private jobSeekerSearch: BehaviorSubject<HomeSearchResponse> = new BehaviorSubject<HomeSearchResponse>(null);
  private hrSearchResponse: BehaviorSubject<HrSearchResponse> = new BehaviorSubject<HrSearchResponse>(null);
  constructor(private httpService: HttpService) {
  }

  public homeSearch(query: string): Observable<HomeSearchResponse> {
    return this.httpService.GET<HomeSearchResponse>(
      Endpoints.HOME_SEARCH + query)
  }

  public applyPageSearch(query: string): Observable<ApplySearchResponse> {
    return this.httpService.GET<ApplySearchResponse>(
      Endpoints.APPLY_PAGE_SEARCH + query,tokenHeader(TokenType.JWT))
  }
  everyOneSearch(event: any) {
    if (event.target.value != null) {
      this.homeSearchFunc(event);
    }
  }
  hrSearch(event: any) {
    this.homeSearchFunc(event)
    this.applyPageSearchFunc(event);
    this.hrSearchResponse.next({
      applyAdvertisement:this.applySearchResponse?.applyAdvertisement,
      advertisement:this.homeSearchResponse?.advertisement,
      jobSeeker:this.homeSearchResponse?.jobSeeker
    });
  }
  private applyPageSearchFunc(event: any) {
    this.applyPageSearch(event.target.value).subscribe({
      next: value => {
        this.applySearchResponse = value;
      }, error: err => {
        console.log(err)
      }
    })
  }

  private homeSearchFunc(event: any) {
    this.homeSearch(event.target.value).subscribe({
      next: value1 => {
        this.homeSearchResponse = value1;
        this.jobSeekerSearch.next({
          jobSeeker:this.homeSearchResponse?.jobSeeker,
          advertisement:this.homeSearchResponse?.advertisement
        })
        console.log(value1)
      }, error: err => {
        console.log(err)
      }
    })
  }
  public getHrSearchResponse(): Observable<HrSearchResponse> {
    return this.hrSearchResponse.asObservable();
  }
  public getJobSeekerSearchResponse(): Observable<HomeSearchResponse> {
    return this.jobSeekerSearch.asObservable();
  }

}
