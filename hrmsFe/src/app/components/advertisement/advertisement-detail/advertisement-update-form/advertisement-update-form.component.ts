import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {GetAdvertisementResponse} from "../../../../model/getAdvertisementResponse";
import {AdvertisementService} from "../../../../services/advertisement.service";
import {UpdateAdvertisementRequest} from "../../../../model/requests/updateAdvertisementRequest";
import {PersonalSkillService} from "../../../../services/personal-skill.service";
import {GetPersonalSkillResponse} from "../../../../model/getPersonalSkillResponse";
import {AuthService} from "../../../../services/auth.service";
import {TokenType} from "../../../../model/token-type.enum";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-advertisement-update-form',
  templateUrl: './advertisement-update-form.component.html',
  styleUrls: ['./advertisement-update-form.component.css']
})
export class AdvertisementUpdateFormComponent implements OnInit {

  advertisementDetail: GetAdvertisementResponse;
  updatedAdvertisement:UpdateAdvertisementRequest
  advertisementId: string
  personalSkillId: string
  personalSkillList:GetPersonalSkillResponse[];

  @ViewChild('automaticButton', { static: false }) automaticButtonRef!: ElementRef;
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private advertisementService: AdvertisementService,
    private personalSkillService: PersonalSkillService,
    protected authService: AuthService,
    private toastr:ToastrService
  ) {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["id"])
      this.advertisementId = params["id"];
    })
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      advCode: [""],
      title: [""],
      jobDescription: [""],
      activationTime: [""],
      offDate: [""],
      personalSkill:[[]]
    });
    this.advertisementService.findById(this.advertisementId).subscribe({
      next:value => {
        console.log(value)
        this.advertisementDetail = value;

      },
      error:err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value.personalSkill)
      this.updatedAdvertisement = this.form.value;
      this.updatedAdvertisement.id = this.advertisementId;
      this.updatedAdvertisement.personalSkillIds = [this.personalSkillId]
      console.log(this.updatedAdvertisement.personalSkillIds)
      this.advertisementService.updateAdvertisement(this.updatedAdvertisement).subscribe({
        next:value => {
          this.toastr.success(`Updated Advertisement Successfully`,"UPDATE")
        },
        error:err => {
          this.toastr.error(`${err.error}`,"An Error Occured.")
        }
      })
    } else {
      this.form.markAllAsTouched();
    }
    this.routeAdvertisement()
  }


  routeAdvertisement() {
    this.router.navigate(['/advertisement']);
  }

  onSelectionChange(event: any) {
    console.log(event.target.value)
    this.personalSkillId = (event.target as HTMLSelectElement).value;
    this.form.get('personalSkill')?.setValue([this.personalSkillId]);

  }
  protected findAllPersonalSkill() {
    this.personalSkillService.findAllPersonalSkill().subscribe({
      next: value => {
        console.log(value);
        this.personalSkillList = value;
      }, error: err => {
        this.toastr.error(`${err.error}`,"An Error Occured.")
      }
    })
  }

  handleManualButtonClick() {
    setTimeout(() => {
      this.automaticButtonRef.nativeElement.click();
    }, 500);
  }
}
