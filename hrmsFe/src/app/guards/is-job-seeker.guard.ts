import {inject} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "../services/auth.service";
import {TokenType} from "../model/token-type.enum";
import {ToastrService} from "ngx-toastr";

// TODO kullanıcıdan urlı ıste ve sonra urlden kaz
export const IsJobSeekerGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const authService = inject(AuthService);
  const toastrService = inject(ToastrService);
  const router = inject(Router);
  if (authService.isAuthenticated(TokenType.ACCESS)){
    return true;
  }
  else{
    router.navigate(["login"]);
    toastrService.error("Please SignIn or SignUp","Redirect to Login")
    return false;

  }
}

