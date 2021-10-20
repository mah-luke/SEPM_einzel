import {Component, forwardRef, OnDestroy, OnInit} from '@angular/core';
import {Sex} from '../../enums/sex';
import {
  ControlValueAccessor,
  FormBuilder,
  FormGroup,
  NG_VALIDATORS,
  NG_VALUE_ACCESSOR,
  Validators
} from '@angular/forms';
import {Subscription} from 'rxjs';
import {HorseFormValues} from '../../dto/horseFormValues';


@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html',
  styleUrls: ['./horse-form.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => HorseFormComponent),
      multi: true
    },
    {
      provide: NG_VALIDATORS,
      useExisting: forwardRef(() => HorseFormComponent),
      multi: true
    }
  ]
})
export class HorseFormComponent implements OnInit, ControlValueAccessor, OnDestroy {
  subscriptions: Subscription[] = [];
  sexes: Sex[] = Object.values(Sex);
  formGroup: FormGroup;
  date = new Date().toISOString().slice(0, 10);

  constructor(private formBuilder: FormBuilder) {
    this.formGroup = this.formBuilder.group({
      name: [null, [Validators.required, Validators.nullValidator]],
      description: [null],
      dob: [null, Validators.required],
      sex: [null, Validators.required],
      food: [null],
      father: [null],
      mother: [null]
    });

    this.subscriptions.push(
      this.formGroup.valueChanges.subscribe(value => {
        this.onChange(value);
        this.onTouched();
      })
    );
  }

  get value(){
    return this.formGroup.value;
  }

  set value(value: HorseFormValues) {
    this.formGroup.setValue(value);
  }

  onTouched: any = () => {};

  onChange: any = () => {};


  writeValue(value: HorseFormValues): void {
      if (value) {
        this.value = value;
      }

      if (value === null) {
        this.formGroup.reset();
      }
  }

  registerOnChange(fn: any): void {
      this.onChange = fn;
  }
  registerOnTouched(fn: any): void {
      this.onTouched = fn;
  }

  validate(_: any) {
    return this.formGroup.valid ? null : {profile: {valid: false}};
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.formGroup.reset();
  }

  inputOk(formName: string): boolean {
    const form = this.formGroup.get(formName);
    return form.valid || form.pristine;
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe());
  }
}
