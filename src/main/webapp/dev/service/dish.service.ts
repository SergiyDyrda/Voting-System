import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";
import {Dish} from "../model/dish.model";
import {reqOptions, restaurantsPath, adminPath, reqOptionsJson, dishesPath, basePath} from "../shared/config";
import {Restaurant} from "../model/restaurant.model";



@Injectable()
export class DishService {

    constructor(private http: Http) {
    }

    loadAllDish(restaurant: Restaurant): Observable<Dish[]> {
        return this.http.get(basePath + restaurantsPath + '/' + restaurant.id + dishesPath, reqOptions)
            .map((res) => {
                return res.json();
            });
    }

    deleteDish(dish: Dish, restaurantId: number): Observable<Response> {
        return this.http.delete(basePath + adminPath + restaurantsPath + '/' + restaurantId +
            dishesPath + '/' + dish.id, reqOptions);
    }

    save(dish: Dish, restaurantId: number): Observable<Response> {
        if (dish.id) {
            return this.update(dish, restaurantId);
        }
        else {
            return this.http.post(basePath + adminPath + restaurantsPath + '/' +
                restaurantId + dishesPath, JSON.stringify(dish), reqOptionsJson);
        }
    }

    private update(dish: Dish, restaurantId: number): Observable<Response> {
        return this.http.put(basePath + adminPath + restaurantsPath + '/' +
            restaurantId + dishesPath, JSON.stringify(dish), reqOptionsJson);
    }

}