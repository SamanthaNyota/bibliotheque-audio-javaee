package fr.lteconsulting.training.javaee;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.lteconsulting.training.javaee.discogs.DiscogsArtist;
import fr.lteconsulting.training.javaee.discogs.DiscogsArtistReleasesResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsArtistResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsArtistSearchResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsRelease;
import fr.lteconsulting.training.javaee.discogs.DiscogsReleaseResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsSecrets;
import fr.lteconsulting.training.javaee.discogs.DiscogsTrack;
import fr.lteconsulting.training.javaee.discogs.DiscogsWebService;
import fr.lteconsulting.training.javaee.ejb.AuteurDAO;
import fr.lteconsulting.training.javaee.ejb.ChansonDAO;
import fr.lteconsulting.training.javaee.ejb.DisqueDAO;
import fr.lteconsulting.training.javaee.entity.Auteur;
import fr.lteconsulting.training.javaee.entity.Chanson;
import fr.lteconsulting.training.javaee.entity.Disque;
import fr.lteconsulting.training.javaee.entity.MaisonDeDisque;

@RunWith( Arquillian.class )
public class DiscogsTest
{
	@EJB
	private AuteurDAO auteurDao;

	@EJB
	private DisqueDAO disqueDao;

	@EJB
	private ChansonDAO chansonDao;

	@Deployment
	public static Archive<?> createDeployment()
	{
		return ShrinkWrap.create( WebArchive.class, "test.war" )
				.addPackage( DiscogsWebService.class.getPackage() )
				.addPackage( AuteurDAO.class.getPackage() )
				.addPackage( MaisonDeDisque.class.getPackage() )
				.addAsResource( "test-persistence.xml", "META-INF/persistence.xml" )
				.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );
	}

	@Test
	public void testDiscogsDeBase() throws Exception
	{
		String term = "Toto";

		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target( "https://api.discogs.com" );

		DiscogsWebService discogsService = target.proxy( DiscogsWebService.class );

		DiscogsArtistSearchResponse response = discogsService.searchArtist( term, "artist", DiscogsWebService.KEY, DiscogsWebService.SECRET, DiscogsSecrets.userAgent() );
		System.out.println( response );

		if( response == null || response.getResults() == null || response.getResults().isEmpty() )
		{
			System.out.println( "No artist found with name like " + term );
			return;
		}

		for( DiscogsArtist artist : response.getResults() )
		{
			System.out.println( "Found artist " + artist.getId() + " = " + artist.getTitle() );

			Auteur auteur = new Auteur();
			auteur.setNom( artist.getTitle() );
			auteurDao.ajouter( auteur );

			try
			{
				DiscogsArtistResponse artistResponse = discogsService.getArtist( artist.getId(), DiscogsSecrets.userAgent() );
				System.out.println( "Found artist details : " + artistResponse );

				DiscogsArtistReleasesResponse artistsReleasesResponse = discogsService.getArtistReleases( artist.getId(), DiscogsSecrets.userAgent() );
				System.out.println( "Found artist releases details : " + artistsReleasesResponse );

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
								System.out.println( "Found track " + track );

								// TODO etre sur d'insérer les chansons dans l'ordre

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
				System.out.println( "Error when calling Discogs" );
				e.printStackTrace();
			}
		}
	}
}
