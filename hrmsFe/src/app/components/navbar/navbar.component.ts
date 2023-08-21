import {Component, Input, OnInit, Output} from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {TokenType} from "../../model/token-type.enum";
import {Router} from "@angular/router";
import {SearchService} from "../../services/search.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {HomeSearchResponse} from "../../model/homeSearchResponse";
import {ApplySearchResponse} from "../../model/applySearchResponse";
import {HrSearchResponse} from "../../model/hrSearchResponse";
import {HttpClient} from "@angular/common/http";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent{
  jwtTokenType: TokenType = TokenType.JWT
  accessTokenType: TokenType = TokenType.ACCESS


  constructor(
    protected authService: AuthService,
    private router: Router,
    protected searchService: SearchService,
    private http:HttpClient,
    private toastr: ToastrService
  ) {
  }




  onInputClick() {
    this.router.navigate(["searchBar"])
  }






}

