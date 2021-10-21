import {Component, forwardRef, OnInit} from '@angular/core';
import {
  ControlValueAccessor,
  FormBuilder,
  FormControl,
  FormGroup,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';
import {FoodService} from '../../service/food.service';
import {Food} from '../../dto/food';
import {FoodQuery} from '../../dto/foodQuery';
import {debounceTime, distinctUntilChanged} from 'rxjs';

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
  searchResult: Array<Food>;
  emptySearchResult: Array<Food>;
  form: FormGroup;
  selecting = false;
  private selected: Food;

  get value(): Food {
    return this.selected;
  }

  set value(value: Food) {
    this.selected = value;
    this.onChange(value);
    this.onTouched();
  }

  constructor(private service: FoodService, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      name: [null]
    });

    this.form.get('name').valueChanges.pipe(
      debounceTime(400),
      distinctUntilChanged()
    ).subscribe( () => {
      if (this.form.get('name').value){
        this.fetchFood();
      }
      else {
        this.searchResult = this.emptySearchResult;
      }
    });
  }

  onChange: any = () => {};

  onTouched: any = () => {};

  writeValue(value: Food): void {
        if (value) {
          this.value = value;
        }

        if (value === null) {
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

  enableSelecting() {
    this.selecting = true;
    if (!this.searchResult) {
      this.fetchFood();
    }
  }

  fetchFood(): void {
    console.log('Fetching food via FoodQuery: ', this.form.value);
    const query: FoodQuery = this.form.value;
    query.limit = 5;

    this.service.getAll(query).subscribe({
      next: data => {
        console.log('recieved query: ', data);
        this.searchResult = data;
        if(!query.name){
          this.emptySearchResult = data;
        }
      },
      error: error => {
        console.error('Error fetching search result', error.message);
      }
    });
  }

  select(food: Food) {
    console.log('selected: ', food);
    this.value = food;
    this.selecting = false;
  }

  reset() {
    this.form.get('name').reset();
    this.value=null;
    this.selecting=false;
  }
}
