package fr.lteconsulting.training.javaee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import fr.lteconsulting.training.javaee.entity.MaisonDeDisque;
import fr.lteconsulting.training.javaee.webservice.AuteurWebService;
import fr.lteconsulting.training.javaee.webservice.ChansonWebService;
import fr.lteconsulting.training.javaee.webservice.DisqueWebService;
import fr.lteconsulting.training.javaee.webservice.MaisonDeDisqueWebService;

@RunWith( Arquillian.class )
public class MiscellanousPersistenceTest
{
	@Deployment
	public static Archive<?> createDeployment()
	{
		// TODO Ajouter la data source pour les tests et l'utiliser à la place de ExempleDS dans le fichier persistence.xml
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
		utx.commit();

		em.clear();

		startTransaction();
	}

	@After
	public void commitTransaction() throws Exception
	{
		utx.commit();
	}

	@Test
	public void shouldFindAllGamesUsingJpqlQuery() throws Exception
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

		MaisonDeDisque maison = em.find( MaisonDeDisque.class, maisonDto.getId() );

		assertNotNull( maison );
		assertEquals( "MAISON", maison.getNom() );
		assertEquals( 1, maison.getAuteurs().size() );

		Auteur auteur = maison.getAuteurs().get( 0 );
		assertNotNull( auteur );
		assertEquals( auteurDto.getId(), auteur.getId() );
		assertEquals( "AUTEUR", auteur.getNom() );
	}

	private void startTransaction() throws Exception
	{
		utx.begin();
		em.joinTransaction();
	}
}
