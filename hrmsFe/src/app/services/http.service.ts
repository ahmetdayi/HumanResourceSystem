import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  //  ngOnInit(): void {
  //         this.searchService.homeSearch("ahmet").subscribe(value => {
  //           console.log(value);
  //         });
  //     }
  constructor(private http : HttpClient) { }

  public POST<T>(url: string, data: any, headers?: HttpHeaders): Observable<T> {
    return this.http.post<T>(url,data, { headers: headers });
  }

  public PUT<T>(url: string, data?: any, headers?: HttpHeaders): Observable<T> {
    return this.http.put<T>(url, data, { headers: headers });
  }

  public DELETE(url: string, data?: any, headers?: HttpHeaders): Observable<any> {
    return this.http.delete<any>(url, { headers: headers, body: data });
  }

  public GET<T>(url: string, headers?: HttpHeaders): Observable<T> {
    return this.http.get<T>(url, { headers: headers });
  }

}
