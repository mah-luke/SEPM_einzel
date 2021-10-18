import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-show-error',
  templateUrl: './show-error.component.html',
  styleUrls: ['./show-error.component.scss']
})
export class ShowErrorComponent implements OnInit {

  @Input() error: HttpErrorResponse;
  @Output() errorChange = new EventEmitter();

  constructor() { }

  ngOnInit(): void {
  }

  public vanishError(): void {
    this.error = null;
    this.errorChange.emit(this.error);
  }
}
