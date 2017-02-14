import {Component, ViewChild, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {EditRestaurantComponent} from "./restaurant-edit.component";
import {Restaurant} from "../../model/restaurant.model";
import {RestaurantService} from "../../service/restaurant.service";
import {DishListComponent} from "../dish/dish-list.component";
import {AuthService} from "../../service/auth.service";
import {ExceptionService} from "../../service/exception.service";
import {Router} from "@angular/router";


@Component({
    templateUrl: '../../../templates/restaurant/restaurant-list.html'
})
export class RestaurantListComponent implements OnInit {

    restaurantsHolder: Observable<Restaurant[]>;

    @ViewChild(EditRestaurantComponent)
    private editRestaurantChild: EditRestaurantComponent;

    @ViewChild(DishListComponent)
    private dishListComponent: DishListComponent;


    constructor(private restaurantService: RestaurantService,
                private authService: AuthService,
                private exceptionService: ExceptionService,
                private router: Router) {
    }

    ngOnInit() {
        this.restaurantsHolder = this.restaurantService.loadAllRestaurants();
    }

    updateList() {
        this.restaurantsHolder = this.restaurantService.loadAllRestaurants();
    }

    deleteItem(restaurant: Restaurant) {
        this.restaurantService.deleteRestaurant(restaurant).subscribe(res => {
            this.updateList();
        });
    }

    onRestaurantMenu(restaurant: Restaurant) {
        this.dishListComponent.restaurant = this.dishListComponent.restaurant == null ? restaurant as Restaurant : null;
        if (this.dishListComponent.restaurant) {
            this.dishListComponent.updateList();
        }
    }

    vote(restaurant: Restaurant) {
        this.restaurantService.vote(restaurant).subscribe(
            res => {
                this.updateList();
            },
            err => {
                this.exceptionService.onError(err);
            });
    }

    showCreateModal() {
        this.editRestaurantChild.resetForm();
        this.editRestaurantChild.showToggle = true;
    }

    selectRestaurant(restaurant) {
        this.editRestaurantChild.showToggle = true;
        this.editRestaurantChild.fillMyForm(restaurant.data);
    }

    save(restaurant: Restaurant) {
        this.editRestaurantChild.showToggle = false;
        this.restaurantService.save(restaurant).subscribe(
            res => {
                this.updateList();
            }
        );
    }
}