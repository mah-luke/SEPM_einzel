import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from '@angular/forms';
import {FoodService} from '../../service/food.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Food} from '../../dto/food';

@Component({
  selector: 'app-search-food',
  templateUrl: './search-food.component.html',
  styleUrls: ['./search-food.component.scss']
})
export class SearchFoodComponent implements OnInit {
  @Output() ngModel: EventEmitter<Food> = new EventEmitter<Food>();
  // @Input() form: FormGroup;
  selected: Food;

  searchResult: Array<Food>;
  focused = false;
  error: HttpErrorResponse;

  form = new FormGroup({
    name: new FormControl(null)
  });


  constructor(private service: FoodService) { }

  ngOnInit(): void {
  }

  fetchFood(): void {
    this.service.getAll(this.form.value).subscribe({
      next: data => {
        console.log('recieved query: ', data);
        this.searchResult = data;
      },
      error: error => {
        console.error('Error fetching search result', error.message);
        this.error = error;
      }
    });
  }

  onSubmit() {
    console.log('submit: ', this.form.value);
    this.fetchFood();
  }

  select(food: Food) {
    console.log('selected: ', food);
    this.selected = food;
    this.ngModel.emit(food);
  }

  unselect() {
    this.selected = null;
  }

  focusIn() {
    this.focused = true;
  }

  focusOut() {
    this.focused = false;
  }

  reset() {
    this.searchResult=null;
    this.select(null);
  }
}
