import {Component, OnInit} from '@angular/core';
import {GetApplyAdvertisementResponse} from "../../../model/getApplyAdvertisementResponse";
import {HttpService} from "../../../services/http.service";
import {ApplyAdvertisementService} from "../../../services/apply-advertisement.service";
import {ToastrService} from "ngx-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'app-private-apply-advertisement',
  templateUrl: './private-apply-advertisement.component.html',
  styleUrls: ['./private-apply-advertisement.component.css']
})
export class PrivateApplyAdvertisementComponent implements OnInit{
  jobSeekerId: string = localStorage.getItem("jobSeekerId")

  applyAdvertisement: GetApplyAdvertisementResponse[];

  constructor(
    private applyAdvertisementService: ApplyAdvertisementService,
    private toastr: ToastrService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.findAllApplying();
  }

  routeAdvertisementDetail(id:string){
    this.router.navigate(["/applyAdvertisement",id])
  }
  findAllApplying() {
    this.applyAdvertisementService.findByJobSeekerId(this.jobSeekerId).subscribe({
      next: value => {
        console.log(value);
        this.applyAdvertisement = value;
      }, error: err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }


}
