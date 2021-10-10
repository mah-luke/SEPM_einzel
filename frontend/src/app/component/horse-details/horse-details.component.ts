import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {HorseService} from '../../service/horse.service';
import {Sex} from '../../enums/sex';
import {Horse} from '../../dto/horse';

@Component({
  selector: 'app-horse-details',
  templateUrl: './horse-details.component.html',
  styleUrls: ['./horse-details.component.scss']
})
export class HorseDetailsComponent implements OnInit {
  id: string;
  sexes: Sex[] = Object.values(Sex);
  error = null;
  model: Horse;
  deletedHorse: Horse;

  constructor(private service: HorseService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.id = params.get('id');
      this.loadHorse();
    });
  }

  public loadHorse() {
    this.service.getHorse(this.id).subscribe( {
      next: data => {
        console.log('horse retrieved', data);
        this.model = data;
      },
      error: error => {
        console.error('Cannot edit horse: ', error);
        this.error = error;
      }
    });
  }

  public deleteHorse() {
    this.service.deleteHorse(this.id).subscribe( {
      next: data => {
        console.log('horse deleted', data);
        this.model = null;
        this.deletedHorse = data;
      },
      error: error => {
        console.error('Cannot delete horse: ', error);
        this.error = error;
      }
    });
  }
}
