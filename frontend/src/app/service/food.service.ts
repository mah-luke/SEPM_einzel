import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Food} from '../dto/food';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {FoodData} from '../dto/foodData';
import {FoodQuery} from '../dto/foodQuery';

const baseUri = environment.backendUrl + '/food';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private http: HttpClient) { }

  /**
   * Get all horses stored in the system by search parameter params
   *
   * @param params the object containing the search parameters
   */
  getAll(params: FoodQuery): Observable<Food[]> {
    let httpParams = new HttpParams();
    for (const key in params){
      if (params[key]) {
        httpParams = httpParams.set(key, params[key]);
      }
    }

    return this.http.get<Food[]>(baseUri, {params: httpParams});
  }

  /**
   * Get a food stored in the system by its id
   *
   * @param id the id of the stored food
   */
  getFood(id: string) {
    return this.http.get<Food>(baseUri + '/' + id);
  }

  /**
   * Creates a food and stores it in the system
   *
   * @param food the food to create
   */
  createFood(food: FoodData): Observable<Food> {
    return this.http.post<Food>(baseUri, food);
  }


}
