import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';
import {Horse} from '../dto/horse';
import {HorseData} from '../dto/horseData';

const baseUri = environment.backendUrl + '/horses';

@Injectable({
  providedIn: 'root'
})
export class HorseService {

  constructor(
    private http: HttpClient,
  ) { }

  /**
   * Get all horses stored in the system
   *
   * @return observable list of found horses.
   */
  getAll(): Observable<Horse[]> {
    return this.http.get<Horse[]>(baseUri);
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
}
