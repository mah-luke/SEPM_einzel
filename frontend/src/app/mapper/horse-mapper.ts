import {HorseData} from '../dto/horseData';
import {Horse} from '../dto/horse';
import {Injectable} from '@angular/core';
import {HorseFormValues} from '../dto/horseFormValues';

@Injectable({
  providedIn: 'root'
})
export class HorseMapper {
  public horseToHorseData(horse: Horse): HorseData {
    if (!horse) {
      return null;
    }
    return new HorseData(
      horse.name,
      horse.description,
      horse.dob,
      horse.sex,
      horse.food?.id
    );
  }
  public horseToFormValues(horse: Horse): HorseFormValues {
    if (!horse) {
      return null;
    }
    return new HorseFormValues(
      horse.name,
      horse.description,
      horse.dob,
      horse.sex,
      horse.food
    );
  }

  public formValuesToHorseData(formValues: HorseFormValues): HorseData {
    if (!formValues) {
      return null;
    }
    return new HorseData(
      formValues.name,
      formValues.description,
      formValues.dob,
      formValues.sex,
      formValues.food?.id
    );

  }


}
