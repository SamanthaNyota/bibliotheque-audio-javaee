package fr.lteconsulting.training.javaee.ejb;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import fr.lteconsulting.training.javaee.discogs.DiscogsArtist;
import fr.lteconsulting.training.javaee.discogs.DiscogsArtistReleasesResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsArtistResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsArtistSearchResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsRelease;
import fr.lteconsulting.training.javaee.discogs.DiscogsReleaseResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsSecrets;
import fr.lteconsulting.training.javaee.discogs.DiscogsTrack;
import fr.lteconsulting.training.javaee.discogs.DiscogsWebService;
import fr.lteconsulting.training.javaee.entity.Auteur;
import fr.lteconsulting.training.javaee.entity.Chanson;
import fr.lteconsulting.training.javaee.entity.Disque;

@Stateless
public class DiscogsImportation
{
	private Logger log = Logger.getLogger( DiscogsImportation.class.getSimpleName() );

	@EJB
	private AuteurDAO auteurDao;

	@EJB
	private DisqueDAO disqueDao;

	@EJB
	private ChansonDAO chansonDao;

	private DiscogsWebService discogsService;

	// Cette annotation appelle la méthode une fois que l'injection du composant est réalisée
	@PostConstruct
	private void initRestEastClient()
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target( "https://api.discogs.com" );

		discogsService = target.proxy( DiscogsWebService.class );
	}

	public List<DiscogsArtist> searchArtists( String searchTerm )
	{
		DiscogsArtistSearchResponse response = discogsService.searchArtist( searchTerm, "artist", DiscogsWebService.KEY, DiscogsWebService.SECRET, DiscogsSecrets.userAgent() );
		log.info( "SEARCH ARTISTS : " + response );

		if( response == null )
			return null;

		return response.getResults();
	}

	public Integer importArtist( int id )
	{
		Auteur auteur = null;

		try
		{
			// TODO d'abord vérifier que l'auteur n'a pas déjà été importé
			// en ajoutant une colonne 'discogsArtistId' contenant l'id Discogs dans la table auteur

			DiscogsArtistResponse artistResponse = discogsService.getArtist( id, DiscogsSecrets.userAgent() );
			log.info( "Found artist details : " + artistResponse );

			auteur = new Auteur();
			auteur.setNom( artistResponse.getName() );
			auteurDao.ajouter( auteur );

			DiscogsArtistReleasesResponse artistsReleasesResponse = discogsService.getArtistReleases( id, DiscogsSecrets.userAgent() );
			log.info( "Found artist releases details : " + artistsReleasesResponse );

			if( artistsReleasesResponse != null && artistsReleasesResponse.getReleases() != null )
			{
				for( DiscogsRelease release : artistsReleasesResponse.getReleases() )
				{
					Disque disque = new Disque();
					disque.setNom( release.getTitle() );
					disqueDao.ajouter( disque );

					DiscogsReleaseResponse releaseResponse = discogsService.getRelease( release.getId(), DiscogsSecrets.userAgent() );

					if( releaseResponse != null && releaseResponse.getTracklist() != null )
					{
						for( DiscogsTrack track : releaseResponse.getTracklist() )
						{
							log.info( "Found track " + track );

							// TODO etre sur d'insérer les chansons dans l'ordre
							// Collections.sort(...) et implémenter un Comparator

							Chanson chanson = new Chanson();
							chanson.setDisque( disque );
							chanson.setAuteur( auteur );
							chanson.setNom( track.getTitle() );
							chansonDao.ajouter( chanson );
						}
					}
				}
			}
		}
		catch( Exception e )
		{
			log.warning( "Error when calling Discogs when importing artist " + id );
		}

		// TODO On ne se préoccupe pas de savoir si toutes les infos (disque+chansons) ont été importées.
		// Il faudra mettre en place un système qui permette de ne pas surcharger Discogs...

		if( auteur == null )
			return null;

		return auteur.getId();
	}
}
