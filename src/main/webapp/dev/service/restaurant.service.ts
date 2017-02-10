import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs";
import {Restaurant} from "../model/restaurant.model";
import {reqOptions, restaurantsPath, basePath, adminPath, reqOptionsJson} from "../shared/config";



@Injectable()
export class RestaurantService {

    constructor(private http: Http) {
    }

    loadAllRestaurants(): Observable<Restaurant[]> {
        return this.http.get(basePath + restaurantsPath, reqOptions)
            .map((res) => {
                return res.json();
            });
    }

    deleteRestaurant(restaurant: Restaurant): Observable<Response> {
        return this.http.delete(basePath + adminPath + restaurantsPath + '/' + restaurant.id, reqOptions);
    }

    vote(restaurant: Restaurant): Observable<Response> {
        return this.http.get(basePath + restaurantsPath + '/vote/' + restaurant.id, reqOptions);
    }

    save(restaurant: Restaurant): Observable<Response> {
        if (restaurant.id) {
            return this.update(restaurant);
        }
        else {
            return this.http.post(basePath + adminPath + restaurantsPath, JSON.stringify(restaurant), reqOptionsJson);
        }
    }

    private update(restaurant: Restaurant): Observable<Response> {
        return this.http.put(basePath + adminPath + restaurantsPath, JSON.stringify(restaurant), reqOptionsJson);
    }

}