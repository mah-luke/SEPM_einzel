import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {HttpErrorResponse} from '@angular/common/http';
import {Food} from '../../dto/food';
import {StringMap} from '@angular/compiler/src/compiler_facade_interface';
import {FormControl, FormGroup} from '@angular/forms';
import {Sex} from '../../enums/sex';

@Component({
  selector: 'app-horse',
  templateUrl: './horse.component.html',
  styleUrls: ['./horse.component.scss']
})
export class HorseComponent implements OnInit {
  search = false;
  sexes = Object.values(Sex);
  horses: Horse[];
  deletedHorse: Horse;
  error: HttpErrorResponse;
  params: StringMap = {};
  form = new FormGroup({
    name: new FormControl(null),
    dob: new FormControl(null),
    sex: new FormControl(null),
    foodId: new FormControl(null)
  });

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
    this.reloadHorses();
  }

  reloadHorses() {
    this.service.getAll(this.form.value).subscribe({
      next: data => {
        console.log('received horses', data);
        this.horses = data;
      },
      error: error => {
        console.error('Error fetching horses', error.message);
        this.error = error;
      }
    });
  }

  onSubmit() {
    this.reloadHorses();
  }
  // ASK: how to do subtemplate for form??

  // ASK: is request sending with every keystroke ok??

  setFoodId(value: Food): void {
    const formCtrl = this.form.get('foodId');

    if(value == null) {
      formCtrl.setValue(null);
    } else {
      formCtrl.setValue(value.id);
    }
      formCtrl.markAsTouched();
      formCtrl.markAsDirty();
  }

  public deleteHorse(id: number) {
    this.service.deleteHorse(String(id)).subscribe( {
      next: data => {
        console.log('horse deleted', data);
        console.log(this.horses.filter(val => val.id !== id));
        this.horses = this.horses.filter(val => val.id !== id);
        this.deletedHorse = data;
      },
      error: error => {
        console.error('Cannot delete horse: ', error);
        this.error = error;
      }
    });
  }
}
