package fr.lteconsulting.training.javaee.ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import fr.lteconsulting.training.javaee.discogs.DiscogsArtistReleasesResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsArtistResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsRelease;
import fr.lteconsulting.training.javaee.discogs.DiscogsReleaseResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsSecrets;
import fr.lteconsulting.training.javaee.discogs.DiscogsTrack;
import fr.lteconsulting.training.javaee.discogs.DiscogsWebService;
import fr.lteconsulting.training.javaee.entity.Auteur;
import fr.lteconsulting.training.javaee.entity.Chanson;
import fr.lteconsulting.training.javaee.entity.Disque;

@Singleton
public class DiscogsImportationJob
{
	private Logger logger = Logger.getLogger( DiscogsImportationJob.class.getSimpleName() );

	private int nbConsecutiveErrors = 0;
	private List<Integer> toImportArtistsId = new ArrayList<>();
	private Integer artisteAImporter = null;
	private Auteur auteurCourant = null;
	private DiscogsArtistReleasesResponse artistsReleasesResponse = null;

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
		ResteasyWebTarget target = client.target( DiscogsWebService.URL );

		discogsService = target.proxy( DiscogsWebService.class );
	}

	public void registerArtistImportationJob( int discogsArtistId )
	{
		toImportArtistsId.add( discogsArtistId );
	}

	@Schedule( second = "*/10", minute = "*", hour = "*" )
	public void work()
	{
		try
		{
			if( artisteAImporter == null || auteurCourant == null )
			{
				if( toImportArtistsId.isEmpty() )
					return;

				artisteAImporter = toImportArtistsId.remove( 0 );

				logger.info( "Discogs importation time for artist " + artisteAImporter + "!" );

				DiscogsArtistResponse artistResponse = discogsService.getArtist( artisteAImporter, DiscogsSecrets.userAgent() );
				logger.info( "Found artist details : " + artistResponse );

				auteurCourant = new Auteur();
				auteurCourant.setNom( artistResponse.getName() );
				auteurDao.ajouter( auteurCourant );
			}
			else if( artistsReleasesResponse == null )
			{
				artistsReleasesResponse = discogsService.getArtistReleases( artisteAImporter, DiscogsSecrets.userAgent() );
				logger.info( "Found artist releases details : " + artistsReleasesResponse );
			}
			else
			{
				if( artistsReleasesResponse.getReleases() == null || artistsReleasesResponse.getReleases().isEmpty() )
				{
					logger.info( "finished to import artist's discs !" );

					raz();
				}
				else
				{
					logger.info( "importing release" );

					DiscogsRelease release = artistsReleasesResponse.getReleases().remove( 0 );

					DiscogsReleaseResponse releaseResponse = discogsService.getRelease( release.getId(), DiscogsSecrets.userAgent() );
					if( releaseResponse != null )
					{
						Disque disque = new Disque();
						disque.setNom( release.getTitle() );
						disqueDao.ajouter( disque );

						if( releaseResponse.getTracklist() != null )
						{
							for( DiscogsTrack track : releaseResponse.getTracklist() )
							{
								logger.info( "Found track " + track );

								// TODO etre sur d'insérer les chansons dans l'ordre
								// Collections.sort(...) et implémenter un Comparator

								Chanson chanson = new Chanson();
								chanson.setDisque( disque );
								chanson.setAuteur( auteurCourant );
								chanson.setNom( track.getTitle() );
								chansonDao.ajouter( chanson );
							}
						}
					}
				}
			}

			nbConsecutiveErrors = 0;
		}
		catch( Exception e )
		{
			int maxErrors = 4;

			nbConsecutiveErrors++;
			if( nbConsecutiveErrors >= maxErrors )
			{
				logger.warning( maxErrors + " times an error, skip this artist " + artisteAImporter );
				raz();
			}
			else
			{
				logger.warning( "Error when calling Discogs when importing artist " + artisteAImporter );
			}
		}
	}

	private void raz()
	{
		nbConsecutiveErrors = 0;
		artistsReleasesResponse = null;
		auteurCourant = null;
		artisteAImporter = null;
	}
}
