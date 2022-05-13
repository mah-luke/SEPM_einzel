import {Component, OnInit, ViewChild} from '@angular/core';
import {FoodData} from '../../dto/foodData';
import {NgForm} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {Food} from '../../dto/food';
import {FoodService} from '../../service/food.service';

@Component({
  selector: 'app-create-food',
  templateUrl: './create-food.component.html',
  styleUrls: ['./create-food.component.scss']
})
export class CreateFoodComponent implements OnInit {
  @ViewChild('foodForm') public form: NgForm;

  model: FoodData = new FoodData();
  submitted = false;
  error: HttpErrorResponse = null;
  createdFood: Food;

  constructor(private service: FoodService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.submitted = true;
    this.createFood();
  }

  createFood() {
    this.service.createFood(this.model).subscribe( {
      next: data => {
        console.log('food created', data);
        this.createdFood = data;
      },
      error: error => {
        console.error('Cannot create food: ', error);
        this.error = error;
      }
    });
  }

  newFood() {
    this.submitted = false;
    this.model = new FoodData();
    this.createdFood = null;
    this.form.reset();
    this.error = null;
  }
}
