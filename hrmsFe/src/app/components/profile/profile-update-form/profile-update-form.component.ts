import {Component, OnInit} from '@angular/core';
import {GetJobSeekerResponse} from "../../../model/getJobSeekerResponse";
import {ActivatedRoute, Router} from "@angular/router";
import {JobSeekerService} from "../../../services/job-seeker.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UpdateJobSeekerRequest} from "../../../model/requests/updateJobSeekerRequest";
import {ToastrService} from "ngx-toastr";


@Component({
  selector: 'app-profile-update-form',
  templateUrl: './profile-update-form.component.html',
  styleUrls: ['./profile-update-form.component.css']
})
export class ProfileUpdateFormComponent implements OnInit {

  form: FormGroup;
  jobSeekerId: string;
  jobSeekerDetail: GetJobSeekerResponse;

  updatedJobSeekerDetail: UpdateJobSeekerRequest;

  constructor(private activatedRoute: ActivatedRoute,
              private jobSeekerService: JobSeekerService,
              private formBuilder: FormBuilder,
              private router: Router,
              private toastr: ToastrService
  ) {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["id"])
      this.jobSeekerId = params["id"];
    })
  }

  ngOnInit(): void {
    this.findJobSeekerById();
    this.form = this.formBuilder.group({
      firstname: [""],
      lastName: [""],
      description: [""],
      birthDay: [""],
    });
  }

  onSubmit() {
    if (this.form.valid) {
      this.updatedJobSeekerDetail = this.form.value;
      this.updatedJobSeekerDetail.id=this.jobSeekerId;
      console.log(this.updatedJobSeekerDetail)
      this.updateJobSeeker();
      this.routeProfile();
    } else {
      this.form.markAllAsTouched();
    }
  }


  routeProfile() {
    this.router.navigate(['/profile']);
  }
  private findJobSeekerById() {
    this.jobSeekerService.findJobSeekerById(this.jobSeekerId).subscribe({
      next: value => {
        console.log(value);
        this.jobSeekerDetail = value;
      },
      error: err => {
        console.log(err)
      }
    });
  }
  updateJobSeeker(){
    this.jobSeekerService.updateJobSeeker(this.updatedJobSeekerDetail).subscribe({
      next:value => {
        this.toastr.success('Job Seeker Updated.', 'UPDATE');
      },
      error:err => {
        this.toastr.error('An Error Occured.Please Try Again.', 'Error');
      }
    })
  }


}
