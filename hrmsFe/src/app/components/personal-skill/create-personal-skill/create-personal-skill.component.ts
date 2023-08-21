import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PersonalSkillService} from "../../../services/personal-skill.service";
import {CreatePersonalSkillRequest} from "../../../model/requests/createPersonalSkillRequest";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-create-personal-skill',
  templateUrl: './create-personal-skill.component.html',
  styleUrls: ['./create-personal-skill.component.css']
})
export class CreatePersonalSkillComponent implements OnInit{
  form: FormGroup
  createPersonalSkillRequest: CreatePersonalSkillRequest;

  constructor(
    private formBuilder: FormBuilder,
    private personalSkillService: PersonalSkillService,
    private router: Router,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      name: ["",Validators.required],
      level: ["",Validators.required]
    });

  }

  private createPersonalSkill() {
    this.personalSkillService.createPersonalSkill(this.createPersonalSkillRequest).subscribe({
      next: value => {
        this.toastr.success('Personal Skill Create.', 'CREATE');
      },
      error: err => {
        this.toastr.error('An Error Occured. Please Try Again.', 'LOGIN');
      }
    })
  }

  onSubmit() {
    if (this.form.valid) {
      this.createPersonalSkillRequest = this.form.value;
      this.createPersonalSkill();
    } else {
      this.form.markAllAsTouched();
    }
    this.routeHome()
  }
  private routeHome() {
    this.router.navigate(["/home"])
  }
}
