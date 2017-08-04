export interface Auteur {
    id: number
    nom: string
    maisonDeDisqueId: number
}

export interface Disque {
    id: number
    nom: string
    chansons?: Chanson[]
}

export interface Chanson {
    id: number
    nom: string
    duree: number
    auteurId: number
    disqueId: number
}

export class ImportedDiscogsArtist {
    id: number
    discogsId: number
}

export class MaisonDeDisque {
    id: number
    nom: string
}

export class PossibleDiscogsArtistImport {
    discogId: number
    artistName: string
}