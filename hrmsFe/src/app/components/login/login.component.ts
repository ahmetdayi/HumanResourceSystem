import {Component, OnInit} from '@angular/core';
import {Endpoints} from "../../utility/endpoints";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginRequest} from "../../model/requests/loginRequest";

import {AuthService} from "../../services/auth.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  loginRequest: LoginRequest;
  form: FormGroup; // Form verilerini saklamak için nesne

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required]]
    });


  }

  onSubmit() {
    if (this.form.valid) {
      this.loginRequest=this.form.value;
      console.log(this.form.value.username)

      this.login();
    }else {
      this.form.markAllAsTouched();
    }
  }

  private login() {
    this.authService.loginInForm(this.loginRequest).subscribe({
      next: value => {
        this.toastr.success('Login is Successful.', 'LOGIN');
        localStorage.clear();
        localStorage.setItem("jwtToken", value["jwtToken"])
        localStorage.setItem("refreshToken", value["refreshToken"])
        setTimeout(this.redirectToHome, 1700)
      }, error: err => {
        console.log(err)
        this.toastr.error('Username or Password Incorrect.Please Try Again', 'LOGIN');
      }
    })
  }

  redirectToLinkedIn(): void {
    window.location.href = Endpoints.GET_PROFILE;
  }
  redirectToHome = () => {
    window.location.href = Endpoints.HOME; // Endpoints.HOME yerine direkt URL'yi belirtin
  };
}
