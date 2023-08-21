import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError, switchMap} from 'rxjs/operators';
import {AuthService} from "../services/auth.service";
import {TokenType} from "../model/token-type.enum";


@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private authService: AuthService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authService.isAuthenticated(TokenType.JWT)) {
      request = this.addAuthToken(request, this.authService.getAccessToken());
    }

    return next.handle(request).pipe(
      catchError((error) => {
        if (error.status === 401 && this.authService.hasRefreshToken()) {
          console.log("refresh")
          return this.authService.getRefreshToken().pipe(
            switchMap(() => {
              request = this.addAuthToken(request, this.authService.getAccessToken());
              return next.handle(request);
            }),
            catchError((refreshError) => {
              return throwError(refreshError);
            })
          );
        } else {
          return throwError(error);
        }
      })
    );
  }

  private addAuthToken(request: HttpRequest<any>, token: string): HttpRequest<any> {
    return request.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
  }
}
