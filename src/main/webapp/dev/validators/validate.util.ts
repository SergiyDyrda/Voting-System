


import {FormControl} from "@angular/forms";

export class ValidateUtil {

    static validateDishCalories(formControl: FormControl) {
        console.log("validator min/max calories");
        if (formControl.value < 10 || formControl.value > 5000) {
            return {error: true}
        }
        return null;
    }

}

