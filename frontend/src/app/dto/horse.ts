import {Sex} from '../enums/sex';
import {Food} from './food';
import {ShallowHorse} from './ShallowHorse';

export interface Horse {
  id: number;
  name: string;
  description?: string;
  dob: Date;
  sex: Sex;
  food?: Food;
  father?: ShallowHorse;
  mother?: ShallowHorse;
}
