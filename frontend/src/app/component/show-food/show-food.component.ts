import {Component, Input, OnInit} from '@angular/core';
import {FoodData} from '../../dto/foodData';
import {Food} from '../../dto/food';

@Component({
  selector: 'app-show-food',
  templateUrl: './show-food.component.html',
  styleUrls: ['./show-food.component.scss']
})
export class ShowFoodComponent implements OnInit {

  @Input() model: Food | FoodData;

  constructor() { }

  ngOnInit(): void {
  }

}
