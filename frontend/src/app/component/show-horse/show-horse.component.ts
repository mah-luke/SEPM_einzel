import {Component, Input, OnInit} from '@angular/core';
import {Horse} from '../../dto/horse';

@Component({
  selector: 'app-show-horse',
  templateUrl: './show-horse.component.html',
  styleUrls: ['./show-horse.component.scss']
})
export class ShowHorseComponent implements OnInit {

  @Input() model: Horse;

  constructor() { }

  ngOnInit(): void {
  }

}
