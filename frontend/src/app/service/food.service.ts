import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Food} from '../dto/food';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {FoodData} from '../dto/foodData';
import {StringMap} from '@angular/compiler/src/compiler_facade_interface';

const baseUri = environment.backendUrl + '/food';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private http: HttpClient) { }

  getAll(params: StringMap): Observable<Food[]> {
    let httpParams = new HttpParams();
    for (const key in params){
      if (params[key]) {
        httpParams = httpParams.set(key, params[key]);
      }
    }

    return this.http.get<Food[]>(baseUri, {params: httpParams});
  }

  getFood(id: string) {
    return this.http.get<Food>(baseUri + '/' + id);
  }

  createFood(food: FoodData): Observable<Food> {
    return this.http.post<Food>(baseUri, food);
  }


}
