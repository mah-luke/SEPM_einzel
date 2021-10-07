import {Sex} from '../enums/sex';

export interface Horse {
  id: number;
  name: string;
  description?: string;
  dob: Date;
  sex: Sex;
  foodId: number;
}
