import {Sex} from '../enums/sex';
import {Food} from './food';

export interface Horse {
  id: number;
  name: string;
  description?: string;
  dob: Date;
  sex: Sex;
  food: Food;
}
