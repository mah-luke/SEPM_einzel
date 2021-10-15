import {Component, OnInit} from '@angular/core';
import {HorseService} from '../../service/horse.service';
import {Horse} from '../../dto/horse';
import {HttpErrorResponse} from '@angular/common/http';
import {FormControl} from '@angular/forms';
import {HorseData} from '../../dto/horseData';
import {HorseMapper} from '../../mapper/horse-mapper';
import {HorseFormValues} from '../../dto/horseFormValues';

@Component({
  selector: 'app-create-horse',
  templateUrl: './create-horse.component.html',
  styleUrls: ['./create-horse.component.scss']
})
export class CreateHorseComponent implements OnInit {
  createdHorse: Horse;
  submitted = false;
  error: HttpErrorResponse;
  form = new FormControl(null);

  constructor(private service: HorseService, private mapper: HorseMapper) {
  }

  onSubmit() {
    const formValues: HorseFormValues = this.form.value;
    console.log('submitted horse creation form', formValues);
    this.submitted = true;
    this.createHorse(this.mapper.formValuesToHorseData(formValues));
  }

  createHorse(horse: HorseData) {
    this.service.createHorse(horse).subscribe( {
      next: data => {
        console.log('horse created', data);
        this.createdHorse = data;
      },
      error: error => {
        console.error('Cannot create horse: ', error);
        this.error = error;
      }
    });
  }


  ngOnInit(): void {
  }

  public newHorse() {
    this.vanishError();
    this.form.reset();
    this.submitted = false;
    this.createdHorse = null;
  }

  public vanishError(): void {
    this.error = null;
  }
}
