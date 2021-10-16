import {Sex} from '../enums/sex';

export class HorseQuery {
  constructor(
    public name?: string,
    public description?: string,
    public dob?: Date,
    public sex?: Sex,
    public foodId?: number,
    public limit?: number
  ) {
  }

}
