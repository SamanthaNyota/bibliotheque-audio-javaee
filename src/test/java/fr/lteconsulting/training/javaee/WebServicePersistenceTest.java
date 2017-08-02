package fr.lteconsulting.training.javaee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.lteconsulting.training.javaee.dto.AuteurDTO;
import fr.lteconsulting.training.javaee.dto.ChansonDTO;
import fr.lteconsulting.training.javaee.dto.DisqueDTO;
import fr.lteconsulting.training.javaee.dto.MaisonDeDisqueDTO;
import fr.lteconsulting.training.javaee.ejb.MaisonDeDisqueDAO;
import fr.lteconsulting.training.javaee.entity.Auteur;
import fr.lteconsulting.training.javaee.entity.Chanson;
import fr.lteconsulting.training.javaee.entity.Disque;
import fr.lteconsulting.training.javaee.entity.MaisonDeDisque;
import fr.lteconsulting.training.javaee.webservice.AuteurWebService;
import fr.lteconsulting.training.javaee.webservice.ChansonWebService;
import fr.lteconsulting.training.javaee.webservice.DisqueWebService;
import fr.lteconsulting.training.javaee.webservice.MaisonDeDisqueWebService;

@RunWith( Arquillian.class )
public class WebServicePersistenceTest
{
	@Deployment
	public static Archive<?> createDeployment()
	{
		return ShrinkWrap.create( WebArchive.class, "test.war" )
				.addPackage( MaisonDeDisque.class.getPackage() )
				.addPackage( MaisonDeDisqueDAO.class.getPackage() )
				.addPackage( MaisonDeDisqueDTO.class.getPackage() )
				.addPackage( MaisonDeDisqueWebService.class.getPackage() )
				.addAsResource( "test-persistence.xml", "META-INF/persistence.xml" )
				.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );
	}

	@PersistenceContext
	private EntityManager em;

	@Inject
	private UserTransaction utx;

	@Inject
	private AuteurWebService auteurWebService;

	@Inject
	private ChansonWebService chansonWebService;

	@Inject
	private DisqueWebService disqueWebService;

	@Inject
	private MaisonDeDisqueWebService maisonDeDisqueWebService;

	@Before
	public void preparePersistenceTest() throws Exception
	{
		startTransaction();

		System.out.println( "Deleting old records" );
		em.createQuery( "delete from Chanson" ).executeUpdate();
		em.createQuery( "delete from Disque" ).executeUpdate();
		em.createQuery( "delete from Auteur" ).executeUpdate();
		em.createQuery( "delete from MaisonDeDisque" ).executeUpdate();

		validateAndRestartTransaction();
	}

	@After
	public void commitTransaction() throws Exception
	{
		utx.commit();
	}

	@Test
	public void verifyEntityGraphAfterCreateByWebService() throws Exception
	{
		MaisonDeDisqueDTO maisonDto = new MaisonDeDisqueDTO();
		maisonDto.setNom( "MAISON" );
		maisonDto = maisonDeDisqueWebService.create( maisonDto );

		AuteurDTO auteurDto = new AuteurDTO();
		auteurDto.setNom( "AUTEUR" );
		auteurDto.setMaisonDeDisqueId( maisonDto.getId() );
		auteurDto = auteurWebService.create( auteurDto );

		DisqueDTO disqueDto = new DisqueDTO();
		disqueDto.setNom( "DISQUE" );
		disqueDto = disqueWebService.create( disqueDto );

		ChansonDTO chansonDto = new ChansonDTO();
		chansonDto.setNom( "CHANSON" );
		chansonDto.setDuree( 60 );
		chansonDto.setAuteurId( auteurDto.getId() );
		chansonDto.setDisqueId( disqueDto.getId() );
		chansonDto = chansonWebService.create( chansonDto );

		validateAndRestartTransaction();

		MaisonDeDisque maison = em.find( MaisonDeDisque.class, maisonDto.getId() );

		assertNotNull( maison );
		assertEquals( maisonDto.getNom(), maison.getNom() );

		List<Auteur> auteurs = maison.getAuteurs();
		assertEquals( 1, auteurs.size() );

		Auteur auteur = auteurs.get( 0 );
		assertNotNull( auteur );
		assertEquals( auteurDto.getId(), (int) auteur.getId() );
		assertEquals( auteurDto.getNom(), auteur.getNom() );

		List<Chanson> chansons = auteur.getChansons();
		assertEquals( 1, chansons.size() );

		Chanson chanson = chansons.get( 0 );
		assertEquals( chansonDto.getNom(), chanson.getNom() );
		assertEquals( chansonDto.getDuree(), chanson.getDuree() );
		assertEquals( chansonDto.getId(), (int) chanson.getId() );

		Disque disque = chanson.getDisque();
		assertEquals( 1, disque.getChansons().size() );
		assertEquals( disqueDto.getId(), (int) disque.getId() );
		assertEquals( disqueDto.getNom(), disque.getNom() );
	}

	private void validateAndRestartTransaction() throws Exception
	{
		utx.commit();
		em.clear();
		startTransaction();
	}

	private void startTransaction() throws Exception
	{
		utx.begin();
		em.joinTransaction();
	}
}
