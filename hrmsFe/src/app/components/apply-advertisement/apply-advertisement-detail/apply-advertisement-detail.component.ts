import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ApplyAdvertisementService} from "../../../services/apply-advertisement.service";
import {AuthService} from "../../../services/auth.service";
import {GetApplyAdvertisementResponse} from "../../../model/getApplyAdvertisementResponse";
import {TokenType} from "../../../model/token-type.enum";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-apply-advertisement-detail',
  templateUrl: './apply-advertisement-detail.component.html',
  styleUrls: ['./apply-advertisement-detail.component.css']
})
export class ApplyAdvertisementDetailComponent implements OnInit {
  applyAdvertisementId: string;
  applyAdvertisementDetail: GetApplyAdvertisementResponse;
  tokenType: TokenType = TokenType.JWT;
  jobSeekerId: string;
  advertisementId: string;
  selectedStatue: string;

  constructor(private activatedRoute: ActivatedRoute,
              private applyAdvertisementService: ApplyAdvertisementService,
              public authService: AuthService,
              private router: Router,
              private toastr: ToastrService
  ) {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["id"])
      this.applyAdvertisementId = params["id"];

    })
  }

  ngOnInit(): void {
    this.findByApplyAdvertisementId();
  }

  onSelectionChange(event: any) {
    this.selectedStatue = event.target.value;
  }
  protected updateStatue() {
    this.applyAdvertisementService.updateApplyAdvertisementStatue(this.selectedStatue, this.applyAdvertisementId).subscribe({
      next: value => {
        this.toastr.success('Update statue ' + `${this.selectedStatue}`, 'UPDATE');
      },
      error: err => {
        this.toastr.error(`${err.error}`, 'An Error Occured.Please Try Again');
      }
    })
  }

  private findByApplyAdvertisementId() {
    this.applyAdvertisementService.findByApplyAdvertisementId(this.applyAdvertisementId).subscribe({
      next: value => {
        this.applyAdvertisementDetail = value;
        this.jobSeekerId = value.jobSeeker.id;
        this.advertisementId = value.advertisement.id;

      },
      error: err => {
        this.toastr.error(`${err.error}`,"An Error Occuredd.")
      }
    })
  }

  routeJobSeeker() {
    this.router.navigate(['/profile', this.jobSeekerId]);
  }

  routeAdvertisement(){
    this.router.navigate(['/advertisement', this.advertisementId]);
  }

}
