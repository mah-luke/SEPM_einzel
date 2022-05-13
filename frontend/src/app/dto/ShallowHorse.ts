import {Sex} from '../enums/sex';

export interface ShallowHorse {
  id: number;
  name: string;
  description?: string;
  dob: Date;
  sex: Sex;
}
