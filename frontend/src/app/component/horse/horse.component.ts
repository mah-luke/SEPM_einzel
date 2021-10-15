import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {HttpErrorResponse} from '@angular/common/http';
import {StringMap} from '@angular/compiler/src/compiler_facade_interface';
import {FormControl, FormGroup} from '@angular/forms';
import {Sex} from '../../enums/sex';
import {HorseQuery} from '../../dto/horseQuery';

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
    food: new FormControl(null)
  });

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
    this.reloadHorses();
  }

  reloadHorses() {
    const query = new HorseQuery(
      this.form.get('name').value,
      null,
      this.form.get('dob').value,
      this.form.get('sex').value,
      this.form.get('food').value?.id
    );

    this.service.getAll(query).subscribe({
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
