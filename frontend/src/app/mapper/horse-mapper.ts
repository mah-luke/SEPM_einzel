import {HorseData} from '../dto/horseData';
import {Horse} from '../dto/horse';
import {Injectable} from '@angular/core';
import {HorseFormValues} from '../dto/horseFormValues';
import {HorseQuery} from '../dto/horseQuery';

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
      horse.food?.id,
      horse.father?.id,
      horse.mother?.id
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
      horse.food,
      horse.father,
      horse.mother
    );
  }

  public formValuesToHorseQuery(formValues: HorseFormValues): HorseQuery {
    if (!formValues) {
      return null;
    }
    return new HorseQuery(
      formValues.name,
      formValues.description,
      formValues.dob,
      formValues.sex,
      formValues.food?.id,
      formValues.father?.id,
      formValues.mother?.id
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
      formValues.food?.id,
      formValues.father?.id,
      formValues.mother?.id
    );

  }


}
