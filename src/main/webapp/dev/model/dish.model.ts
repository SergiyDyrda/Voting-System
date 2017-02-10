

export class Dish {

    id: number;
    name: string;
    description: string;
    calories: number;

    constructor(name: string, description: string, calories: number) {
        this.name = name;
        this.description = description;
        this.calories = calories;
    }
}