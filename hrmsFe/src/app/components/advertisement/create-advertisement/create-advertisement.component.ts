import {Component, ElementRef, ViewChild} from '@angular/core';
import {GetAdvertisementResponse} from "../../../model/getAdvertisementResponse";
import {UpdateAdvertisementRequest} from "../../../model/requests/updateAdvertisementRequest";
import {GetPersonalSkillResponse} from "../../../model/getPersonalSkillResponse";
import {FormBuilder, FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AdvertisementService} from "../../../services/advertisement.service";
import {PersonalSkillService} from "../../../services/personal-skill.service";
import {AuthService} from "../../../services/auth.service";
import {CreateAdvertisementRequest} from "../../../model/requests/createAdvertisementRequest";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-create-advertisement',
  templateUrl: './create-advertisement.component.html',
  styleUrls: ['./create-advertisement.component.css']
})
export class CreateAdvertisementComponent {

  createAdvertisementRequest: CreateAdvertisementRequest;
  personalSkillId: string
  personalSkillList:GetPersonalSkillResponse[];

  @ViewChild('automaticButton', { static: false }) automaticButtonRef!: ElementRef;
  form: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private advertisementService: AdvertisementService,
    private personalSkillService: PersonalSkillService,
    protected authService: AuthService,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      advCode: [""],
      title: [""],
      jobDescription: [""],
      activationTime: [""],
      offDate: [""],
      personalSkill:[[]],
      statue:[""],
      hrId:[""]
    });
  }

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value.personalSkill)
      this.createAdvertisementRequest = this.form.value;
      console.log(this.form.value.hrId)
      this.createAdvertisementRequest.statue = this.form.value.statue.toUpperCase();
      this.createAdvertisementRequest.humanResourceId = this.form.value.hrId;
      this.createAdvertisementRequest.personalSkillIdList = [this.personalSkillId]

      this.advertisementService.createAdvertisement(this.createAdvertisementRequest).subscribe({
        next:value => {
          this.toastr.success('Advertisement Created', 'CREATE');
        },
        error:err => {
          console.log(err)
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
