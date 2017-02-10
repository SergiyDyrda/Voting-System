import {Routes, RouterModule} from "@angular/router";
import {RestaurantListComponent} from "./component/restaurant/restaurant-list.component";
import {AuthActivateGuard} from "./shared/auth.activate.guard";
import {ProfileComponent} from "./component/user/profile.component";
import {RegisterComponent} from "./component/user/register.component";
import {UserListComponent} from "./component/user/user-list.component";
import {EntryComponent} from "./component/auth/entry.component";
import {ModuleWithProviders} from "@angular/core";
import {DishListComponent} from "./component/dish/dish-list.component";



const appRoutes: Routes = [
    {
        path: "",
        pathMatch: "full",
        redirectTo: "/restaurant-list",
    },
    {
        path: "login",
        component: EntryComponent,
    },
    {
        path: "register",
        component: RegisterComponent
    },
    {
        path: "restaurant-list",
        component: RestaurantListComponent,
        canActivate: [AuthActivateGuard],
    },
    {
        path: "dish-list",
        component: DishListComponent,
        canActivate: [AuthActivateGuard],
    },
    {
        path: "profile",
        component: ProfileComponent,
        canActivate: [AuthActivateGuard]
    },
    {
        path: "user-list",
        component: UserListComponent,
        canActivate: [AuthActivateGuard]
    }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(appRoutes, {useHash: true});
