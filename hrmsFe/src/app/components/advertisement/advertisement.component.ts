import {Component, OnInit} from '@angular/core';
import {GetAdvertisementResponse} from "../../model/getAdvertisementResponse";
import {AuthService} from "../../services/auth.service";
import {AdvertisementService} from "../../services/advertisement.service";
import {ToastrService} from "ngx-toastr";
import {TokenType} from "../../model/token-type.enum";

@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  styleUrls: ['./advertisement.component.css']
})
export class AdvertisementComponent implements OnInit{

  allAdvertisement: GetAdvertisementResponse[];

  accessTokenType: TokenType = TokenType.ACCESS;
  jwtTokenType: TokenType = TokenType.JWT;
  constructor(private advertisementService: AdvertisementService,
              private toastr: ToastrService,
              protected authService:AuthService) {
  }
  ngOnInit(): void {

    this.advertisementService.findAllAdvertisement().subscribe({
      next:value => {

        this.allAdvertisement = value;
      },
      error:err => {
        this.toastr.error('An Error Occured.Please Try Again', 'ERROR');
      }
    })
  }



}
