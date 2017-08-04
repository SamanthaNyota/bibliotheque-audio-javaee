package fr.lteconsulting.training.javaee;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.lteconsulting.training.javaee.discogs.DiscogsArtist;
import fr.lteconsulting.training.javaee.discogs.DiscogsWebService;
import fr.lteconsulting.training.javaee.ejb.AuteurDAO;
import fr.lteconsulting.training.javaee.ejb.DiscogsImportation;
import fr.lteconsulting.training.javaee.entity.MaisonDeDisque;

// TODO : il faudrait mocker le service web pour valider les tests
// ainsi nos tests ne dépendraient pas de la présence et du fonctionnement de Discogs
@RunWith( Arquillian.class )
public class DiscogsTest
{
	@EJB
	private DiscogsImportation importation;

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
		List<DiscogsArtist> searchResults = importation.searchArtists( "Toto" );
		assertNotNull( searchResults );
		assertFalse( searchResults.isEmpty() );

		DiscogsArtist artistToImport = searchResults.get( 0 );

		Integer auteurId = importation.importArtist( artistToImport.getId() );
		assertNotNull( auteurId );
		assertTrue( auteurId > 0 );
	}
}
