import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from '../../service/horse.service';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {HorseData} from '../../dto/horseData';
import {FormControl} from '@angular/forms';
import {HttpErrorResponse} from '@angular/common/http';
import {HorseMapper} from '../../mapper/horse-mapper';
import {HorseFormValues} from '../../dto/horseFormValues';

@Component({
  selector: 'app-edit-horse',
  templateUrl: './edit-horse.component.html',
  styleUrls: ['./edit-horse.component.scss']
})
export class EditHorseComponent implements OnInit {
  id: string;
  editedHorse: Horse;
  submitted = false;
  error: HttpErrorResponse;
  form = new FormControl();
  loading = false;

  constructor(private service: HorseService, private route: ActivatedRoute, private mapper: HorseMapper) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.id = params.get('id');
      this.loadHorse();
    });
  }

  onSubmit() {
    const formValue: HorseFormValues = this.form.value;
    console.log('submitted horse editing form', formValue);
    this.submitted = true;
    this.loading = true;
    this.editHorse(
      this.mapper.formValuesToHorseData(formValue)
    );
  }

  editHorse(horse: HorseData) {
    this.service.editHorse(this.id, horse).subscribe( {
      next: data => {
        console.log('horse edited', data);
        this.editedHorse = data;
        this.loading = false;
      },
      error: error => {
        console.error('Cannot edit horse: ', error);
        this.error = error;
        this.loading = false;
      }

    });
  }

  loadHorse() {
    this.service.getHorse(this.id).subscribe( {
      next: data => {
        console.log('horse retrieved', data);
        this.form.setValue(this.mapper.horseToFormValues(data));
      },
      error: error => {
        console.error('Cannot edit horse: ', error);
        this.error = error;
      }
    });
  }

  public reEditHorse() {
    this.vanishError();
    this.loadHorse();
    this.submitted = false;
    this.loading = false;
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
