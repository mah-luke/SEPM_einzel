import {Sex} from '../enums/sex';

export class HorseQuery {
  constructor(
    public name?: string,
    public description?: string,
    public dob?: Date,
    public sex?: Sex,
    public foodId?: number,
    public fatherId?: number,
    public motherId?: number,
    public limit?: number
  ) {
  }

  isEquivalent(other: HorseQuery): boolean {
    if (other) {
      for (const key in other) {
        if (other[key] !== this[key]) {
          return false;
        }
      }

    } else {
      return false;
    }
    return true;
  }

}
