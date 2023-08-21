import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {JobSeekerService} from "../../services/job-seeker.service";
import {AuthService} from "../../services/auth.service";
import {GetJobSeekerResponse} from "../../model/getJobSeekerResponse";
import {Router} from "@angular/router";
import {BlackListService} from "../../services/black-list.service";
import {CreateBlackListRequest} from "../../model/requests/createBlackListRequest";
import {FormBuilder, FormGroup} from "@angular/forms";
import {TokenType} from "../../model/token-type.enum";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-job-seeker',
  templateUrl: './job-seeker.component.html',
  styleUrls: ['./job-seeker.component.css']
})
export class JobSeekerComponent implements OnInit{

  allJobSeeker: GetJobSeekerResponse[];
  blackListRequest: CreateBlackListRequest;
  form: FormGroup;

  tokenType: TokenType.JWT;

  @ViewChild('automaticButton', { static: false }) automaticButtonRef!: ElementRef;

  constructor(
    private jobSeekerService: JobSeekerService,
    protected authService: AuthService,
    private router: Router,
    private blackListService: BlackListService,
    private formBuilder: FormBuilder,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.findAllJobSeeker();
    this.form = this.formBuilder.group({
      description: [""],
      hrId: [""]
    });
  }

  private findAllJobSeeker() {
    this.jobSeekerService.findAllJobSeeker().subscribe({
      next: value => {

        this.allJobSeeker = value;
      }, error: err => {
        this.toastr.error(`${err.error}`, 'An Error Occured. Please Try Again.');
      }
    })
  }

  onSubmit(id: string | undefined) {
    if (this.form.valid) {
      console.log(this.form.value)
      this.blackListRequest = {
        description:this.form.value.description,
        jobSeekerId: id,
        humanResourceId: this.form.value.hrId
      }
    } else {
      this.form.markAllAsTouched();
    }
    this.addInBlackList();
    this.handleManualButtonClick();
    this.refreshPage();
  }
  routeProfile(id: string | undefined){
    this.router.navigate(["profile/",id])
  }
  handleManualButtonClick() {
    setTimeout(() => {
      this.automaticButtonRef.nativeElement.click();
    }, 500);
  }

  addInBlackList() {

  this.blackListService.createBlackList(this.blackListRequest).subscribe({
    next:value => {
      console.log(value);
    },
    error:err => {
      this.toastr.error(`${err.error}`, 'An Error Occured. Please Try Again.');
    }
  })
  }
  refreshPage = ()=>{
  window.location.reload();
  }

  deleteInBlackList(id: string | undefined) {
  this.blackListService.findByJobSeekerId(id).subscribe({
    next:value => {
      this.blackListService.deleteBlackList(value.id).subscribe({
        next:value1 => {
          console.log(value1)
        },error:err => {
          this.toastr.error(`${err.error}`, 'An Error Occured. Please Try Again.');
        }
      })
    },error:err => {
      this.toastr.error(`${err.error}`, 'An Error Occured. Please Try Again.');
    }
  })
    setTimeout(this.refreshPage,400)
  }
}
