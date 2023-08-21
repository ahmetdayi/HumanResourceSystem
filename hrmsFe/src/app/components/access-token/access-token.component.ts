import {AfterViewInit, Component, OnInit} from '@angular/core';
import {HttpHeaders} from "@angular/common/http";
import {HttpService} from "../../services/http.service";
import {Endpoints, tokenHeader} from "../../utility/endpoints";
import {GetJobSeekerResponse} from "../../model/getJobSeekerResponse";
import {TokenType} from "../../model/token-type.enum";
import {catchError, EMPTY, tap} from "rxjs";
import {ToastrService} from "ngx-toastr";


@Component({
  selector: 'app-access-token',
  templateUrl: './access-token.component.html',
  styleUrls: ['./access-token.component.css']
})
export class AccessTokenComponent implements OnInit,AfterViewInit {
  profile: GetJobSeekerResponse;

  constructor(private http: HttpService,private toastr:ToastrService) {
  }

  ngAfterViewInit(): void {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });
    this.getAccessToken(headers).subscribe(() => {
      this.getProfile();
    });
    setTimeout(this.redirectToHome,2500)
  }

  private getProfile() {
    this.http.GET<GetJobSeekerResponse>(Endpoints.GET_PROFILE, tokenHeader(TokenType.ACCESS)).subscribe({
      next: value => {
        this.profile = value;
        localStorage.setItem("jobSeekerId", value.id);
      }, error: err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }

  ngOnInit(): void {
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded'
    });
    this.getAccessToken(headers).subscribe();
  }

  redirectToHome = () => {
    window.location.href = Endpoints.HOME; // Endpoints.HOME yerine direkt URL'yi belirtin
  };

  private getAccessToken(headers: HttpHeaders) {
    return this.http.GET(Endpoints.GET_ACCESS_TOKEN, headers).pipe(
      tap(value => {
        console.log(value);
        localStorage.clear();
        localStorage.setItem("access", `${value}`);
      }),
      catchError(err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
        return EMPTY; // Hata durumunda boş bir Observable döndür
      })
    );
  }
}
