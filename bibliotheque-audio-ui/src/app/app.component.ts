import { Component } from '@angular/core'

import { Observable } from 'rxjs/Observable'
import { Subject } from 'rxjs/Subject'
import 'rxjs/add/observable/of'
import 'rxjs/add/operator/catch'
import 'rxjs/add/operator/debounceTime'
import 'rxjs/add/operator/distinctUntilChanged'
import 'rxjs/add/operator/switchMap'

import { DataService } from './data.service'
import * as Model from './model'

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  auteurs: Observable<Model.Auteur[]>
  selectedAuteur: Model.Auteur

  searchArtistToImport: string
  artistSearchTerms = new Subject<string>()
  importableArtists: Observable<Model.PossibleDiscogsArtistImport[]>

  constructor(private dataService: DataService) {
    this.auteurs = this.dataService.artists()

    this.importableArtists = this.artistSearchTerms
      .debounceTime(300)
      .distinctUntilChanged()
      .switchMap(term => term ? this.dataService.searchArtist(term) : Observable.of<Model.PossibleDiscogsArtistImport[]>([]))
      .catch(error => {
        console.log(error)
        return Observable.of<Model.PossibleDiscogsArtistImport[]>([]);
      })
  }

  rechercheArtiste(term: string): void {
    this.artistSearchTerms.next(term)
  }

  importArtist(artist: Model.PossibleDiscogsArtistImport) {
    this.dataService.importArtist(artist.discogId)
  }
}
