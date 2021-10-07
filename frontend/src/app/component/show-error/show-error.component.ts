import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'app-show-error',
  templateUrl: './show-error.component.html',
  styleUrls: ['./show-error.component.scss']
})
export class ShowErrorComponent implements OnInit {

  @Input() error;
  @Output() errorChange = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  public vanishError(): void {
    this.error = null;
    this.errorChange.emit(this.error);
  }
}
