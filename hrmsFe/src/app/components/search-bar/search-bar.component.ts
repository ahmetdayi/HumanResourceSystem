import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HrSearchResponse} from "../../model/hrSearchResponse";
import {SearchService} from "../../services/search.service";
import {HomeSearchResponse} from "../../model/homeSearchResponse";
import {AuthService} from "../../services/auth.service";
import {TokenType} from "../../model/token-type.enum";

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit{

  hrSearch: HrSearchResponse;
  jobSeekerSearch: HomeSearchResponse
  jwtToken : TokenType = TokenType.JWT
  accessToken : TokenType = TokenType.ACCESS
  constructor(
    private router:Router,
    private searchService: SearchService,
    protected authService:AuthService
  ) {

  }

  ngOnInit(): void {
      this.searchService.getHrSearchResponse().subscribe(response => {
        this.hrSearch = response;
        console.log(this.hrSearch);
      });
      this.searchService.getJobSeekerSearchResponse().subscribe(response =>{
        this.jobSeekerSearch = response
        console.log(this.jobSeekerSearch)
      })

  }
  routeJobSeeker(id:string){
    this.router.navigate(["/profile",id])
  }
  routeAdvertisement(id:string){
    this.router.navigate(["/advertisement",id])
  }
  routeApplyAdvertisement(id:string){
    this.router.navigate(["/applyAdvertisement",id])
  }


}
