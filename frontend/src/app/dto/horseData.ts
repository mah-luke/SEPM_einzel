import {Sex} from '../enums/sex';

export class HorseData {

  constructor(
    public name?: string,
    public dob?: Date,
    public sex?: Sex,
    public foodId?: number,
    public description?: string
  ) {}
}
