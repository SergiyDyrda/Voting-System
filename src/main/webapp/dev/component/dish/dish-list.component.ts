import {Component, ViewChild, OnInit} from "@angular/core";
import {Observable} from "rxjs";
import {EditDishComponent} from "./dish-edit.component";
import {Dish} from "../../model/dish.model";
import {DishService} from "../../service/dish.service";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {Restaurant} from "../../model/restaurant.model";


@Component({
    templateUrl: '../../../templates/dish/dish-list.html',
    selector: 'dish-list'
})
export class DishListComponent implements OnInit {

    dishesHolder: Observable<Dish[]>;

    restaurant: Restaurant;

    @ViewChild(EditDishComponent)
    private editDishChild: EditDishComponent;

    constructor(private dishService: DishService,
                private router: Router,
                private authService: AuthService) {
    }

    ngOnInit() {
        if (this.restaurant != null) {
            this.dishesHolder = this.dishService.loadAllDish(this.restaurant);
        }
    }

    updateList() {
        this.dishesHolder = this.dishService.loadAllDish(this.restaurant);
    }

    deleteItem(dish: Dish) {
        this.dishService.deleteDish(dish, this.restaurant.id).subscribe(res => {
            this.updateList();
        });
    }

    showCreateModal() {
        this.editDishChild.resetForm();
        this.editDishChild.showToggle = true;
    }

    selectDish(dish) {
        this.editDishChild.showToggle = true;
        this.editDishChild.fillMyForm(dish.data);
    }

    save(dish: Dish) {
        this.editDishChild.showToggle = false;
        this.dishService.save(dish, this.restaurant.id).subscribe(
            res => {
                this.updateList();
            }
        );
    }
}