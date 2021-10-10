import {Component, OnInit} from '@angular/core';
import {HorseData} from '../../dto/horseData';
import {HorseService} from '../../service/horse.service';
import {Horse} from '../../dto/horse';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-create-horse',
  templateUrl: './create-horse.component.html',
  styleUrls: ['./create-horse.component.scss']
})
export class CreateHorseComponent implements OnInit {
  createdHorse: Horse;
  submitted = false;
  error: HttpErrorResponse = null;
  model: HorseData = new HorseData();

  constructor(private service: HorseService) {
  }


  onSubmit(model: HorseData) {
    console.log('submitted horse creation form');
    this.model = model;
    this.submitted = true;
    this.createHorse();
  }

  createHorse() {
    this.service.createHorse(this.model).subscribe( {
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
    this.model = new HorseData();
    this.submitted = false;
    this.createdHorse = null;
  }

  public vanishError(): void {
    this.error = null;
  }
}
