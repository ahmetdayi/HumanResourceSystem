import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {GetAdvertisementResponse} from "../../../model/getAdvertisementResponse";
import {CreateApplyAdvertisementRequest} from "../../../model/requests/createApplyAdvertisementRequest";
import {AdvertisementService} from "../../../services/advertisement.service";
import {ApplyAdvertisementService} from "../../../services/apply-advertisement.service";
import {AuthService} from "../../../services/auth.service";
import {TokenType} from "../../../model/token-type.enum";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-advertisement-detail',
  templateUrl: './advertisement-detail.component.html',
  styleUrls: ['./advertisement-detail.component.css']
})
export class AdvertisementDetailComponent implements OnInit {
  advertisementId: string;
  jobSeekerId: string = localStorage.getItem("jobSeekerId");
  applyAdvertisementStatue: string = "processing";
  advertisementDetail: GetAdvertisementResponse;
  applyAdvertisementRequest: CreateApplyAdvertisementRequest;

  advertisementStatue: boolean;
  form:FormGroup;

  accessToken: TokenType = TokenType.ACCESS;
  jwtToken: TokenType = TokenType.JWT;

  @ViewChild('automaticButton', { static: false }) automaticButtonRef!: ElementRef;

  constructor(private activatedRoute: ActivatedRoute,
              private advertisementService: AdvertisementService,
              private applyAdvertisementService: ApplyAdvertisementService,
              public authService: AuthService,
              private formBuilder: FormBuilder,
              private toastr: ToastrService,
              private router: Router
  ) {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["id"])
      this.advertisementId = params["id"];

    })
  }

  ngOnInit(): void {
    this.applyAdvertisementRequest = {
      applyAdvertisementStatue: this.applyAdvertisementStatue,
      advertisementId: this.advertisementId,
      jobSeekerId: this.jobSeekerId

    };
    this.form = this.formBuilder.group({
      statue: ["",Validators.required]

    });
    this.findByAdvertisementId();
  }



  private changeStatue() {
    this.advertisementService.changeAdvertisementStatue(this.advertisementStatue, this.advertisementId).subscribe({
      next: value => {
        this.toastr.success('Change Advertisement statue ' + `${this.advertisementStatue}`, 'ERROR');
      }, error: err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value)

    } else {
      this.form.markAllAsTouched();
    }
    console.log(this.advertisementStatue + " " + this.advertisementId )
    this.changeStatue();
    this.handleManualButtonClick();
    this.refreshPage();

  }
  public createApplyAdvertisement() {

   this.applyAdvertisementService.createApplyAdvertisement(this.applyAdvertisementRequest).subscribe({
      next: value => {
        this.toastr.success('Successful Application.', 'CREATE');
      },error:err => {
       console.log(err.error)
        this.toastr.error('An Error Occuped : ' + `${err.error}`, 'CREATE');

     }
    })
  }

  private findByAdvertisementId() {
    this.advertisementService.findById(this.advertisementId).subscribe({
      next: value => {
        console.log(value)
        this.advertisementDetail = value;
      }, error: err => {
        this.toastr.error(`${err.error}`, 'An Error Occured.');
      }
    })
  }

  onSelectionChange(event: Event) {
    const statue = (event.target as HTMLSelectElement).value;
    console.log(statue)
    if (statue == "ACTIVE"){
      this.advertisementStatue = true;
    }else if (statue == "PASSIVE") {
      this.advertisementStatue = false;
    }

    this.form.get('statue')?.setValue(statue);

  }
  refreshPage = ()=>{
    window.location.reload();
  }
  handleManualButtonClick() {
    setTimeout(() => {
      this.automaticButtonRef.nativeElement.click();
    }, 500);
  }

  routeThisAdvertisementApplication(id:string) {
    this.router.navigate(["/application/",id,"advertisement"])
  }
}
