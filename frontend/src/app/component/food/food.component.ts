import { Component, OnInit } from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';
import {Food} from '../../dto/food';
import {FoodService} from '../../service/food.service';

@Component({
  selector: 'app-food',
  templateUrl: './food.component.html',
  styleUrls: ['./food.component.scss']
})
export class FoodComponent implements OnInit {
  search = false;
  food: Food[];
  error: HttpErrorResponse;

  constructor(private service: FoodService) { }

  ngOnInit(): void {
    this.reloadFood();
  }

  reloadFood() {
    this.service.getAll({}).subscribe({
      next: data => {
        console.log('received food', data);
        this.food = data;
      },
      error: error => {
        console.error('Error fetching food', error.message);
        this.error = error;
      }
    });
  }

}
