import {Injectable} from '@angular/core';
import {Observable, tap, throwError} from 'rxjs';

import {LoginRequest} from "../model/requests/loginRequest";
import {LoginResponse} from "../model/requests/loginResponse";
import {HttpService} from "./http.service";
import {Endpoints, tokenHeader} from "../utility/endpoints";
import {TokenType} from "../model/token-type.enum";
import {HttpHeaders} from "@angular/common/http";
import {catchError} from "rxjs/operators";
import {ToastrService} from "ngx-toastr";


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private accessToken: string | null = null;
  private refreshToken: string | null = null;
  constructor(
    private http: HttpService,
    private toastr: ToastrService) {
  }

  public loginInForm(request: LoginRequest): Observable<LoginResponse> {
    return this.http.POST<LoginResponse>(Endpoints.LOGIN_IN_FORM, request);
  }

  isAuthenticated(tokenType: TokenType) {
    if (localStorage.getItem(tokenType)) {
      return true;
    } else {
      return false;
    }
  }

  tokenHeader(tokenType: TokenType): HttpHeaders {
    if (tokenType == TokenType.ACCESS){
      console.log(localStorage.getItem("access"))
      return new HttpHeaders({
        'Authorization': `Bearer ${localStorage.getItem(tokenType)}`
      })
    }else if (tokenType != TokenType.REFRESH_TOKEN) {
      return new HttpHeaders({
        'Authorization': `Bearer ${localStorage.getItem(tokenType)}`
      })
    } else {
      return new HttpHeaders({
        'Authorization': `Bearer JWT${localStorage.getItem(tokenType)}`
      })
    }
  }
  getRefreshToken(): Observable<any> {
    return this.http.POST('/refreshToken', { refresh_token: this.refreshToken },tokenHeader(TokenType.JWT)).pipe(
      tap((response: any) => {
        this.accessToken = response.access_token;
      }),
      catchError((error) => {
        this.logout(); // Logout on refresh token failure
        return throwError(error);
      })
    );
  }

  isLoggedIn(): boolean {
    return !!this.accessToken;
  }

  hasRefreshToken(): boolean {
    return !!this.refreshToken;
  }

  getAccessToken(): string | null {
    return this.accessToken;
  }
  logout() {
    localStorage.clear();
    this.http.POST("http://localhost:8080/logout",{}).subscribe({
      error:err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }
}
