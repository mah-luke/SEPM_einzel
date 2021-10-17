import {Sex} from '../enums/sex';
import {Food} from './food';
import {Horse} from './horse';

export class HorseFormValues {
  constructor(
    public name: string,
    public description: string,
    public dob: Date,
    public sex: Sex,
    public food: Food,
    public father: Horse,
    public mother: Horse
  ) {}

}
