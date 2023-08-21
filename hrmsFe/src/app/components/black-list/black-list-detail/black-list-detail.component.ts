import {Component, OnInit} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {BlackListService} from "../../../services/black-list.service";
import {ActivatedRoute, Router} from "@angular/router";
import {TokenType} from "../../../model/token-type.enum";
import {GetBlackListResponse} from "../../../model/getBlackListResponse";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-black-list-detail',
  templateUrl: './black-list-detail.component.html',
  styleUrls: ['./black-list-detail.component.css']
})
export class BlackListDetailComponent implements OnInit{
  blackListId: string;
  blacklistDetail: GetBlackListResponse;
  tokenType: TokenType = TokenType.JWT;
  constructor(protected authService: AuthService,
              private blackListService: BlackListService,
              private activatedRoute: ActivatedRoute,
              private router: Router,
              private toastr: ToastrService
  ) {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["id"])
      this.blackListId = params["id"];
    })
  }
  ngOnInit(): void {
    this.blackListService.findByBlackListId(this.blackListId).subscribe({
      next:value => {
        console.log(value)
        this.blacklistDetail = value;
      },error:err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }
  deleteInBlackList(){
    this.blackListService.deleteBlackList(this.blackListId).subscribe({
      next:value => {
        console.log(value)
      },
      error:err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
    this.routeBlackListPage();
  }
  routeJobSeeker() {
    this.router.navigate(['/profile', this.blacklistDetail?.jobSeeker?.id]);
  }
  routeBlackListPage() {
    this.router.navigate(['/blackList', { deleted: true }]);
  }


}
