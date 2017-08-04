import { Injectable } from '@angular/core'
import { Http } from '@angular/http'

import { Observable } from 'rxjs/Observable'
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/toPromise'

import * as Model from './model'

@Injectable()
export class DataService {
    constructor(private http: Http) { }

    artists(): Observable<Model.Auteur[]> {
        return this.http
            .get('http://localhost:8080/bibliotheque-audio-javaee-1.0-SNAPSHOT/api/auteurs')
            .map(response => response.json() as Model.Auteur[])
    }

    searchArtist(term): Observable<Model.PossibleDiscogsArtistImport[]> {
        return this.http
            .get(`http://localhost:8080/bibliotheque-audio-javaee-1.0-SNAPSHOT/api/discogs/searchArtists?q=${term}`)
            .map(response => response.json() as Model.PossibleDiscogsArtistImport[])
    }

    importArtist(discogsArtistId) {
        this.http
            .get(`http://localhost:8080/bibliotheque-audio-javaee-1.0-SNAPSHOT/api/discogs/importArtistAsync?discogsArtistId=${discogsArtistId}`)
            .toPromise()
    }
}