import {Component, Output, EventEmitter, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {Dish} from "../../model/dish.model";
import {ValidateUtil} from "../../validators/validate.util";
import {AuthService} from "../../service/auth.service";

@Component({
    templateUrl: '../../templates/dish/dish-edit.html',
    selector: 'edit-dish'
})
export class EditDishComponent implements OnInit {

    showToggle: boolean = false;

    dishForm: FormGroup;

    @Output()
    onSaveEvent: EventEmitter<Dish> = new EventEmitter<Dish>();

    constructor(private formBuilder: FormBuilder,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.dishForm = this.formBuilder.group({
            id: [''],
            name: [``, Validators.required],
            description: [``, Validators.required],
            calories: [``, Validators.compose([Validators.required, ValidateUtil.validateDishCalories])],
        });
    }

    fillMyForm(dish: Dish) {
        this.dishForm.patchValue(dish);
    }

    resetForm() {
        this.dishForm.reset();
    }

    onSave() {
        let value = this.dishForm.value;
        this.onSaveEvent.emit(value);
        this.dishForm.reset();
    }
}