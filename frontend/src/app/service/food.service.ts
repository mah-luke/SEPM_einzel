import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Food} from '../dto/food';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {FoodData} from '../dto/foodData';

const baseUri = environment.backendUrl + '/food';

@Injectable({
  providedIn: 'root'
})
export class FoodService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Food[]> {
    return this.http.get<Food[]>(baseUri);
  }

  getFood(id: string) {
    return this.http.get<Food>(baseUri + '/' + id);
  }

  createFood(food: FoodData): Observable<Food> {
    return this.http.post<Food>(baseUri, food);
  }


}
