import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from 'src/app/service/horse.service';
import {HttpErrorResponse} from '@angular/common/http';
import {FormControl, FormGroup} from '@angular/forms';
import {Sex} from '../../enums/sex';
import {HorseQuery} from '../../dto/horseQuery';
import {ActivatedRoute, Router} from '@angular/router';
import {HorseMapper} from '../../mapper/horse-mapper';
import {FoodService} from '../../service/food.service';
import {HorseFormValues} from '../../dto/horseFormValues';

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
  form = new FormGroup({
    name: new FormControl(null),
    dob: new FormControl(null),
    sex: new FormControl(null),
    food: new FormControl(null)
  });
  private oldQuery: HorseQuery;

  constructor(
    private service: HorseService,
    private route: ActivatedRoute,
    private router: Router,
    private mapper: HorseMapper,
    private foodService: FoodService
    ) {
      this.form.patchValue(this.route.queryParams.subscribe(params =>{

        if (params.foodId) {
          foodService.getFood(params.foodId).subscribe({
            next: data => {
              const values = new HorseFormValues(params.name, null, params.dob, params.sex, null, null, null);
              console.log('received food', data);
              values.food = data;
              this.form.patchValue(values);
              this.reloadHorses();
            },
            error: error => {
               console.error('Error fetching food', error.message);
              this.error = error;
            }
          });
        } else if (params) {
          this.form.patchValue(params);
          this.reloadHorses();
        }
      }
    ));
  }

  ngOnInit(): void {
  }

  reloadHorses() {
    const query: HorseQuery = this.mapper.formValuesToHorseQuery(this.form.value);
    const cleanedQuery: HorseQuery = this.getCleanQuery(query);

    if (!cleanedQuery.isEquivalent(this.oldQuery)) {
      console.log('Searching with params: ', cleanedQuery);

      this.service.getAll(cleanedQuery).subscribe({
        next: data => {
          console.log('received horses', data);
          this.horses = data;
          this.oldQuery = cleanedQuery;
        },
        error: error => {
          console.error('Error fetching horses', error.message);
          this.error = error;
        }
      });
    }
  }

  getCleanQuery(query) {
    const cleanedQuery = new HorseQuery();
    for (const key in query) {
      if (query[key]) {
        cleanedQuery[key] = query[key];
      }
    }
    return cleanedQuery;
  }

  onSubmit() {
    const query: HorseQuery = this.mapper.formValuesToHorseQuery(this.form.value);
    const cleanedQuery: HorseQuery = this.getCleanQuery(query);

    this.router.navigate(
      [],
      {
        relativeTo: this.route,
        queryParams: cleanedQuery,
      }
    );
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
