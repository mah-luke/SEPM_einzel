import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Sex} from '../../enums/sex';
import {HorseData} from '../../dto/horseData';
import {Horse} from '../../dto/horse';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html',
  styleUrls: ['./horse-form.component.scss']
})
export class HorseFormComponent implements OnInit {
  @ViewChild('horseForm') public sub: NgForm;

  @Input() model: Horse | HorseData;
  @Output() emitter = new EventEmitter<Horse | HorseData>();
  sexes: Sex[] = Object.values(Sex);

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.emitter.emit(this.model);
    this.sub.reset();
    console.log(this.sub.pristine);
  }

}
