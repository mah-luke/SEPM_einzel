import {HttpClient, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Horse} from '../dto/horse';
import {HorseData} from '../dto/horseData';
import {HorseQuery} from '../dto/horseQuery';

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  constructor(
    private http: HttpClient,
  ) { }

  /**
   * Get all horses stored in the system by search parameter param
   *
   * @param params object that contains the search parameters
   * @return observable list of found horses.
   */
  getAll(params: HorseQuery): Observable<Horse[]> {
    let httpParams = new HttpParams();
    for (const key in params) {
      if (params[key]) {
        httpParams = httpParams.set(key, params[key]);
      }
    }
    return this.http.get<Horse[]>(baseUri, {params: httpParams});
  }


  /**
   * Create a new horse in the system
   *
   * @param horse the data of the horse to create
   * @return observable the created horse
   */
  createHorse(horse: HorseData): Observable<Horse> {
    return this.http.post<Horse>(baseUri, horse);
  }

  /**
   * Edit an already existing horse in the system.
   *
   * @param id the id of the horse to edit.
   * @param horse the updated values for the horse.
   * @return observable the edited horse
   */
  editHorse(id: string, horse: HorseData): Observable<Horse> {
    return this.http.put<Horse>(baseUri + '/' + id, horse);
  }

  /**
   * Get a horse stored in the system.
   *
   * @param id the id of the stored horse.
   * @return horse the horse with 'id'
   */
  getHorse(id: string) {
    return this.http.get<Horse>(baseUri + '/' + id);
  }

  /**
   * Delete a horse from the system.
   *
   * @param id the id of the horse to delete.
   * @return horse the deleted horse
   */
  deleteHorse(id: string) {
    return this.http.delete<Horse>(baseUri + '/' + id);
  }
}
