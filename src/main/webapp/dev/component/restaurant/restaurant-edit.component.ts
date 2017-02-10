import {Component, Output, EventEmitter, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {Restaurant} from "../../model/restaurant.model";
import {AuthService} from "../../service/auth.service";

@Component({
    templateUrl: '../../templates/restaurant/restaurant-edit.html',
    selector: 'edit-restaurant'
})
export class EditRestaurantComponent implements OnInit {

    showToggle: boolean = false;

    restaurantForm: FormGroup;

    @Output()
    onSaveEvent: EventEmitter<Restaurant> = new EventEmitter<Restaurant>();

    constructor(private formBuilder: FormBuilder,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.restaurantForm = this.formBuilder.group({
            id: [''],
            name: [``, Validators.required],
            votes: [''],
        });
    }

    fillMyForm(restaurant: Restaurant) {
        this.restaurantForm.patchValue(restaurant);
    }

    resetForm() {
        this.restaurantForm.reset();
    }

    onSave() {
        let value = this.restaurantForm.value;
        this.onSaveEvent.emit(value);
        this.restaurantForm.reset();
    }
}