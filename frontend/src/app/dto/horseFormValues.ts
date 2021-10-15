import {Sex} from '../enums/sex';
import {Food} from './food';

export class HorseFormValues {
  constructor(
    public name: string,
    public description: string,
    public dob: Date,
    public sex: Sex,
    public food: Food
  ) {}

}
