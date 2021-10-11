import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {Sex} from '../../enums/sex';
import {HorseData} from '../../dto/horseData';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-horse-form',
  templateUrl: './horse-form.component.html',
  styleUrls: ['./horse-form.component.scss']
})
export class HorseFormComponent implements OnInit {
  @ViewChild('horseForm') public form: NgForm;

  @Input() model: HorseData;
  @Output() emitter = new EventEmitter<HorseData>();
  sexes: Sex[] = Object.values(Sex);

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.emitter.emit(this.model);
    this.form.reset();
  }

}
