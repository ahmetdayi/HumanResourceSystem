import {Component, OnInit} from '@angular/core';
import {ApplyAdvertisementService} from "../../services/apply-advertisement.service";
import {GetApplyAdvertisementResponse} from "../../model/getApplyAdvertisementResponse";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-apply-advertisement',
  templateUrl: './apply-advertisement.component.html',
  styleUrls: ['./apply-advertisement.component.css']
})
export class ApplyAdvertisementComponent implements OnInit {
  allApplyAdvertisement: GetApplyAdvertisementResponse[];
  changedStatue: string;

  constructor(
    private applyAdvertisementService: ApplyAdvertisementService,
    private toastr: ToastrService
    ) {
  }

  ngOnInit(): void {
    this.findAllApplyAdvertisement();
  }

  private findAllApplyAdvertisement() {
    if (this.changedStatue == null || this.changedStatue == undefined){
      this.applyAdvertisementService.findAllApplyAdvertisement().subscribe({
        next: value => {
          console.log(value)
          this.allApplyAdvertisement = value;
        }, error: err => {
          this.toastr.error(`${err.error}`,"An Error Occured.")
        }
      })
    }
  }

  filterByStatue(event: any) {
  this.changedStatue = event.target.value;
    console.log(this.changedStatue)
  }

  updateStatue() {
   // TODO bı hata var
    if (this.changedStatue == "PROCESSING" || this.changedStatue == "OK" || this.changedStatue == "REJECT") {
      this.applyAdvertisementService.filterApplyAdvertisementByStatue(this.changedStatue).subscribe({
        next: value => {
          this.toastr.success('Filter By ' + `${this.changedStatue}`, 'FILTER');
          this.allApplyAdvertisement = value;
        }, error: err => {
          this.toastr.error(`${err.error}`,"An Error Occured.")
        }
      })
    }else{

      this.findAllApplyAdvertisement();
    }
  }


  sortByMatchCount() {
    this.applyAdvertisementService.sortedApplyAdvertisementByMatchCount().subscribe({
      next:value => {
        this.toastr.success('Sorted Matching Count', 'SORTED');
        this.allApplyAdvertisement = value;
      },error:err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }
}
