import {inject, Injectable} from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivateFn,
  Router,
  RouterStateSnapshot,

} from '@angular/router';
import {AuthService} from "../services/auth.service";
import {TokenType} from "../model/token-type.enum";
import {ToastrService} from "ngx-toastr";


export const isHumanResourceGuard: CanActivateFn = (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => {
  const authService = inject(AuthService);
  const toastrService = inject(ToastrService);
  const router = inject(Router);

  if (authService.isAuthenticated(TokenType.JWT)){
  return true;
}
else{
  toastrService.error("Please SignUp or SignIn","Redirect to Login")
  router.navigate(["login"]);

  return false;

}

}
