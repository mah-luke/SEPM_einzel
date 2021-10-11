import {Component, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';
import {HorseService} from 'src/app/service/horse.service';

@Component({
  selector: 'app-horse',
  templateUrl: './horse.component.html',
  styleUrls: ['./horse.component.scss']
})
export class HorseComponent implements OnInit {
  search = false;
  horses: Horse[];
  error;

  constructor(
    private service: HorseService,
  ) { }

  ngOnInit(): void {
    this.reloadHorses();
  }

  reloadHorses() {
    this.service.getAll().subscribe({
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
}
