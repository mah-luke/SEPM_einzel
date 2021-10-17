import {Component, forwardRef, Input, OnInit} from '@angular/core';
import {
  ControlValueAccessor, FormBuilder, FormControl,
  FormGroup,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
} from '@angular/forms';
import {HorseQuery} from '../../dto/horseQuery';
import {HorseService} from '../../service/horse.service';
import {Horse} from '../../dto/horse';
import {HorseMapper} from '../../mapper/horse-mapper';

@Component({
  selector: 'app-search-horse',
  templateUrl: './search-horse.component.html',
  styleUrls: ['./search-horse.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => SearchHorseComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => SearchHorseComponent),
      multi: true
    }
  ]
})
export class SearchHorseComponent implements OnInit, ControlValueAccessor {

  @Input() set query(value) {
    this.form.patchValue(value);
    console.log(this.form.value);
  };
  form: FormGroup;
  selected: Horse;
  searchResult: Array<Horse>;


  constructor(private formBuilder: FormBuilder, private service: HorseService, private mapper: HorseMapper) {
    this.form = this.formBuilder.group({
      name: [null],
      description: [null],
      dob: [null],
      sex: [null],
      food: [null]
    });
  }

  get value(): Horse {
    return this.selected;
  }

  set value(value: Horse) {
    this.selected = value;
    this.onChange(value);
    this.onTouched();
  }

  onChange: any = () => {};

  onTouched: any = () => {};

  fetchHorses() {
    console.log(this.form.value);
    const query: HorseQuery = this.mapper.formValuesToHorseData(this.form.value);
    query.limit = 5;

    console.log('Querying...:', query);

    this.service.getAll(query).subscribe({
      next: data => {
        console.log('received horses', data);
        this.searchResult = data;
      },
      error: error => {
        console.error('Error fetching horses', error.message);
      }
    });
  }

  onSubmit() {
    console.log('submit: ', this.form.value);
    this.fetchHorses();
  }

  ngOnInit(): void {
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  writeValue(value: Horse): void {
    if (value) {
      this.value = value;
    }

    if (value === null) {
      this.reset();
    }
  }

  reset() {
    this.searchResult = null;
    this.select(null);
    this.form.get('name').reset();
  }

  select(horse: Horse) {
    console.log('selected: ', horse);
    this.value = horse;
    this.onChange(horse);
    this.onTouched();
  }

  unselect() {
    this.value = null;
  }

  validate(_: FormControl) {
    return null;
  }

}
