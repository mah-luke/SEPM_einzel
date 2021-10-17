import {Sex} from '../enums/sex';
import {Food} from './food';

export interface ShallowHorse {
  id: number;
  name: string;
  description?: string;
  dob: Date;
  sex: Sex;
  food?: Food;
  father?: ShallowHorse;
  mother?: ShallowHorse;

}
