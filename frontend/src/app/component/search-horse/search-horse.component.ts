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
import {debounceTime, distinctUntilChanged} from 'rxjs';

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
  };
  form: FormGroup;
  searchResult: Array<Horse>;
  emptySearchResult: Array<Horse>;
  selecting = false;
  private selected: Horse;
  private loadedFor: Date; // needed for checking whether date for fetching has changed


  constructor(private formBuilder: FormBuilder, private service: HorseService) {
    this.form = this.formBuilder.group({
      name: [null],
      sex: [null],
      dob: [null]
    });

    this.form.get('name').valueChanges.pipe(
      debounceTime(400),
      distinctUntilChanged()
    ).subscribe( res => {
      if (this.form.get('name').value){
        this.fetchHorses();
      }
      else {
        this.searchResult = this.emptySearchResult;
      }
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


  fetchHorses() {
    console.log('Fetching horses via horseQuery: ', this.form.value);
    const query: HorseQuery = this.form.value;
    query.limit = 5;

    this.service.getAll(query).subscribe({
      next: data => {
        console.log('received horses', data);
        this.searchResult = data;
        if(!query.name){
          this.emptySearchResult = data;
        }
      },
      error: error => {
        console.error('Error fetching horses', error.message);
      }
    });
  }

  enableSelecting() {
    this.selecting = true;
    if (this.loadedFor !== this.form.get('dob').value){
      console.log('Date has changed... fetching horses');
      this.loadedFor = this.form.get('dob').value;
      this.fetchHorses();
    }
  }

  reset() {
    this.form.get('name').reset();
    this.value=null;
    this.selecting = false;
  }

  select(horse: Horse) {
    console.log('selected: ', horse);
    this.value = horse;
    this.selecting = false;
  }

  ngOnInit(): void {
  }

  onChange: any = () => {};

  onTouched: any = () => {};

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

  validate(_: FormControl) {
    return null;
  }

}
