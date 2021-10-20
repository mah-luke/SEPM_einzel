import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {HttpErrorResponse} from '@angular/common/http';
import {StringMap} from '@angular/compiler/src/compiler_facade_interface';
import {FormControl, FormGroup} from '@angular/forms';
import {Sex} from '../../enums/sex';
import {HorseQuery} from '../../dto/horseQuery';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseMapper} from '../../mapper/horse-mapper';

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
    private route: ActivatedRoute,
    private router: Router,
    private mapper: HorseMapper
  ) {
    this.form.patchValue(this.route.queryParams.subscribe(params =>{
        console.log('Parameters: ', params);
        this.form.patchValue(params);
      }
    ));
    this.reloadHorses();
  }

  ngOnInit(): void {
  }

  reloadHorses() {
    const query: HorseQuery = this.mapper.formValuesToHorseData(this.form.value);
    const cleanedQuery: HorseQuery = {};

    for (const key in query) {
      if (query[key]) {
        cleanedQuery[key] = query[key];
      }
    }

    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: cleanedQuery,
      }
    );

    console.log('Searching with params: ', cleanedQuery);

    this.service.getAll(cleanedQuery).subscribe({
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
