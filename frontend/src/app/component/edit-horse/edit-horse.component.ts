import { Component, OnInit } from '@angular/core';
import {Sex} from '../../enums/sex';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {ActivatedRoute, ParamMap} from '@angular/router';

@Component({
  selector: 'app-edit-horse',
  templateUrl: './edit-horse.component.html',
  styleUrls: ['./edit-horse.component.scss']
})
export class EditHorseComponent implements OnInit {
  id: string;
  sexes: Sex[] = Object.values(Sex);
  editedHorse: Horse;
  submitted = false;
  error = null;
  model: Horse;

  constructor(private service: HorseService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.id = params.get('id');
      this.loadHorse();
    });
  }

  onSubmit(model: Horse) {
    console.log('submitted horse editing form');
    this.model = model;
    this.submitted = true;
    this.editHorse();
  }

  editHorse() {
    this.service.editHorse(this.id, this.model).subscribe( {
      next: data => {
        console.log('horse edited', data);
        this.editedHorse = data;
      },
      error: error => {
        console.error('Cannot edit horse: ', error);
        this.error = error.error;
      }
    });
  }

  loadHorse() {
    this.service.getHorse(this.id).subscribe( {
      next: data => {
        console.log('horse retrieved', data);
        this.model = data;
      },
      error: error => {
        console.error('Cannot edit horse: ', error);
        this.error = error.error;
      }
    });
  }


  public reEditHorse() {
    this.vanishError();
    this.loadHorse();
    this.submitted = false;
    this.editedHorse = null;
  }

  public vanishError(): void {
    this.error = null;
  }

  // private validateId(id: string): number {
  //   logger.info('validating id');
  //   return 0;
  // }
}
