import { Component, OnInit } from '@angular/core';
import {HorseData} from '../../dto/horseData';
import {Sex} from '../../enums/sex';
import {HorseService} from '../../service/horse.service';
import {Horse} from '../../dto/horse';

@Component({
  selector: 'app-create-horse',
  templateUrl: './create-horse.component.html',
  styleUrls: ['./create-horse.component.scss']
})
export class CreateHorseComponent implements OnInit {
  sexes = Object.values(Sex);
  createdHorse: Horse;
  submitted = false;
  error = null;
  model = new HorseData();

  constructor(private service: HorseService) {
  }


  onSubmit() {
    console.log('submitted horse creation form');
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
        console.error(error);
        this.showError(error.error);
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

  private showError(error: Error): void {
    this.error = error.message;
  }
}
