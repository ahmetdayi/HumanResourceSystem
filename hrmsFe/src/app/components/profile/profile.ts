import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {JobSeekerService} from "../../services/job-seeker.service";
import {GetJobSeekerResponse} from "../../model/getJobSeekerResponse";
import {TokenType} from "../../model/token-type.enum";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AddPersonalSkillInJobSeekerRequest} from "../../model/requests/addPersonalSkillInJobSeekerRequest";
import {PersonalSkillService} from "../../services/personal-skill.service";
import {GetPersonalSkillResponse} from "../../model/getPersonalSkillResponse";
import {ToastrService} from "ngx-toastr";
import {tokenHeader} from "../../utility/endpoints";


@Component({
  selector: 'app-profile',
  templateUrl: './profile.html',
  styleUrls: ['./profile.css']
})
export class Profile implements OnInit{
  jobSeekerId: string;
  jobSeekerDetail: GetJobSeekerResponse;
  tokenType:TokenType = TokenType.ACCESS;
  form: FormGroup;
  addPersonalSkillRequest: AddPersonalSkillInJobSeekerRequest;
  personalSkillList: GetPersonalSkillResponse[];

  @ViewChild('automaticButton', { static: false }) automaticButtonRef!: ElementRef;
  constructor(private activatedRoute: ActivatedRoute,
              private jobSeekerService: JobSeekerService,
              public authService: AuthService,
              public personalSkillService: PersonalSkillService,
              public router: Router,
              private formBuilder: FormBuilder,
              private toastr: ToastrService
  ) {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["id"])
      this.jobSeekerId = params["id"];

    })
  }

  ngOnInit(): void {
    if (tokenHeader(TokenType.ACCESS).get("Authorization")  == "Bearer null"){
      this.findByJobSeekerId();
    }else{
      this.jobSeekerService.getProfile().subscribe({
        next:value => {
          this.jobSeekerId = value.id
          this.router.navigate(["/profile",this.jobSeekerId])
          this.jobSeekerDetail = value;
        },error:err => {
          this.toastr.error('There is a Error. Please Try Again.' + `${err.error}`, 'Server Error!');
        }
      })
    }

    this.form = this.formBuilder.group({
      personalSkill: ["" , Validators.required],

    });
  }

  protected findAllPersonalSkill() {
    this.personalSkillService.findAllPersonalSkill().subscribe({
      next: value => {
        console.log(value);
        this.personalSkillList = value;
      }, error: err => {
        this.toastr.error(`${err.error}`,"An Error Occured")
      }
    })
  }

  private findByJobSeekerId() {
    this.jobSeekerService.findJobSeekerById(this.jobSeekerId).subscribe({
      next: value => {
        console.log(value)
        this.jobSeekerDetail = value;
      }, error: err => {
        this.toastr.error(`${err.error}`,"An Error Occured")
      }
    })
  }

  onSubmit() {
    if (this.form.valid) {
      console.log(this.form.value)
      console.log(typeof this.form.value.toString())
      this.addPersonalSkillRequest = {
        jobSeekerId:this.jobSeekerId,
        personalSkillIdList:[this.form.value.personalSkill]
      };

    }else{
      this.form.markAllAsTouched();
    }
    this.addPersonalSkill();
    this.handleManualButtonClick();
    setTimeout(this.refreshPage,1400);
  }

  private addPersonalSkill() {
    console.log("Request " +this.addPersonalSkillRequest?.personalSkillIdList[0])
    console.log("Request " +this.addPersonalSkillRequest?.jobSeekerId)
    this.jobSeekerService.addPersonalSkillInJobSeeker(this.addPersonalSkillRequest).subscribe({
      next: value => {
        console.log("aaaaa")
        this.toastr.success(`Personal Skill Added(If your level is higher than select level ,You see higher)`,"Personal Skill Added")
      }, error: err => {
        this.toastr.error(`${err.error}`,"An Error Occured")
      }
    })
  }

  onSelectionChange(event: Event) {
    const selectedId = (event.target as HTMLSelectElement).value;
    this.form.get('personalSkill')?.setValue(selectedId);

  }
  refreshPage = ()=>{
    window.location.reload();
  }
  handleManualButtonClick() {
    setTimeout(() => {
      this.automaticButtonRef.nativeElement.click();
    }, 500);
  }

}
