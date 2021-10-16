import {Component, forwardRef, OnInit} from '@angular/core';
import { ControlValueAccessor, FormControl, FormGroup, NG_VALIDATORS, NG_VALUE_ACCESSOR,} from '@angular/forms';
import {FoodService} from '../../service/food.service';
import {Food} from '../../dto/food';
import {FoodQuery} from '../../dto/foodQuery';

@Component({
  selector: 'app-search-food',
  templateUrl: './search-food.component.html',
  styleUrls: ['./search-food.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SearchFoodComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => SearchFoodComponent),
      multi: true
    }
  ]
})
export class SearchFoodComponent implements OnInit, ControlValueAccessor {
  selected: Food;
  searchResult: Array<Food>;

  form = new FormGroup({
    name: new FormControl(null)
  });

  get value(): Food {
    return this.selected;
  }

  set value(value: Food) {
    this.selected = value;
    this.onChange(value);
    this.onTouched();
  }

  constructor(private service: FoodService) {
  }

  onChange: any = () => {};

  onTouched: any = () => {};

  writeValue(value: Food): void {
        if (value) {
          this.value = value;
        }

        if (value === null) {
          this.form.reset();
          this.reset();
        }
    }

  validate(_: FormControl) {
    return null;
    // return this.form.valid ? null : { profile: { valid: false } };
  }

  registerOnChange(fn: any): void {
        this.onChange = fn;
    }
  registerOnTouched(fn: any): void {
        this.onTouched = fn;
    }

  ngOnInit(): void {
  }

  fetchFood(): void {
    const val = new FoodQuery(this.form.get('name').value, null, null, 5);

    this.service.getAll(val).subscribe({
      next: data => {
        console.log('recieved query: ', data);
        this.searchResult = data;
      },
      error: error => {
        console.error('Error fetching search result', error.message);
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
    this.onChange(food);
    this.onTouched();
  }

  unselect() {
    this.selected = null;
  }

  reset() {
    this.searchResult=null;
    this.select(null);
    this.form.patchValue({name: null});
  }
}
