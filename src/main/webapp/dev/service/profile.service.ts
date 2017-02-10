import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {UserModel} from "../model/user.model";
import {Injectable} from "@angular/core";
import {reqOptions, profilePath, basePath, reqOptionsJson} from "../shared/config";


@Injectable()
export class ProfileService {


    constructor(private http: Http) {
    }

    getOwnProfile(): Observable<Response> {
        return this.http.get(basePath + profilePath, reqOptions);
    }

    saveOwnProfile(value: UserModel): Observable<Response> {
        return this.http.put(basePath + profilePath, JSON.stringify(value), reqOptionsJson);
    }
}