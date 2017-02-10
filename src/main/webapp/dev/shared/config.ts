import {RequestOptions, Headers} from "@angular/http";


export const basePath: string = '/votingsystem';
export const profilePath: string = '/profile';
export const adminPath: string = '/admin';
export const loginPath: string = "/spring_security_check";
export const restaurantsPath: string = '/restaurants';
export const dishesPath: string = '/dishes';
export const registerPath: string = '/register';
export const usersPath: string = adminPath + '/users';
export const i18nPath: string = '/i18n';


export const headers: Headers = new Headers({
  'Content-Type': 'application/json'
});
export const reqOptions: RequestOptions = new RequestOptions({
  withCredentials: true
});
export const reqOptionsJson: RequestOptions = new RequestOptions({
  withCredentials: true,
  headers: headers
});
