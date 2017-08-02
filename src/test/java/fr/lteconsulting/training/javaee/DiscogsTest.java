package fr.lteconsulting.training.javaee;

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

import fr.lteconsulting.training.javaee.discogs.ArtistSearchResponse;
import fr.lteconsulting.training.javaee.discogs.DiscogsWebService;

@RunWith( Arquillian.class )
public class DiscogsTest
{
	@Deployment
	public static Archive<?> createDeployment()
	{
		return ShrinkWrap.create( WebArchive.class, "test.war" )
				.addPackage( DiscogsWebService.class.getPackage() )
				.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );
	}

	@Test
	public void testDiscogsDeBase() throws Exception
	{
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target( "https://api.discogs.com" );

		DiscogsWebService discogsService = target.proxy( DiscogsWebService.class );

		ArtistSearchResponse response = discogsService.searchArtist( "Toto", "artist", DiscogsWebService.KEY, DiscogsWebService.SECRET, "DiscogsClient/1.0 +http://lteconsulting.fr" );
		System.out.println( response );
	}
}
