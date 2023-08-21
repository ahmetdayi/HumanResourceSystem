import {Component, OnInit} from '@angular/core';
import {ApplyAdvertisementService} from "../../../../services/apply-advertisement.service";
import {ToastrService} from "ngx-toastr";
import {ActivatedRoute, Router} from "@angular/router";
import {GetApplyAdvertisementResponse} from "../../../../model/getApplyAdvertisementResponse";

@Component({
  selector: 'app-advertisement-to-application',
  templateUrl: './advertisement-to-application.component.html',
  styleUrls: ['./advertisement-to-application.component.css']
})
export class AdvertisementToApplicationComponent implements OnInit{

  advertisementId: string;

  applicantAdvertisements:GetApplyAdvertisementResponse[];

  constructor(
    private applyAdvertisementService: ApplyAdvertisementService,
    private toastr: ToastrService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["id"])
      this.advertisementId = params["id"];

    })
  }
  ngOnInit(): void {
    this.showApplicant()
  }

  private showApplicant() {
    this.applyAdvertisementService.findByAdvertisementId(this.advertisementId).subscribe({
      next: value => {
        this.applicantAdvertisements = value;
      }, error: err => {
        this.toastr.error(`${err.error}`, "An Error Occured.")
      }
    });
  }


  routeApplyAdvertisementDetail(id:string) {
    this.router.navigate(["/applyAdvertisement",id])
  }
}
