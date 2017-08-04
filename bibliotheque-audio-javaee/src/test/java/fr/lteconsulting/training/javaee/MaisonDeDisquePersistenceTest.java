package fr.lteconsulting.training.javaee;

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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.lteconsulting.training.javaee.entity.MaisonDeDisque;

@RunWith( Arquillian.class )
public class MaisonDeDisquePersistenceTest
{
	@Deployment
	public static Archive<?> createDeployment()
	{
		// TODO Ajouter la data source pour les tests et l'utiliser à la place de ExempleDS dans le fichier persistence.xml
		return ShrinkWrap.create( WebArchive.class, "test.war" )
				.addPackage( MaisonDeDisque.class.getPackage() )
				.addAsResource( "test-persistence.xml", "META-INF/persistence.xml" )
				.addAsWebInfResource( EmptyAsset.INSTANCE, "beans.xml" );
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	@Before
	public void preparePersistenceTest() throws Exception
	{
		startTransaction();
		System.out.println( "Deleting old records" );
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
		System.out.println( "Inserting record" );
		MaisonDeDisque maison = new MaisonDeDisque();
		maison.setNom( "TotoRecords" );
		em.persist( maison );

		System.out.println( "Selecting (using JPQL)..." );
		List<MaisonDeDisque> maisons = em.createQuery( "from MaisonDeDisque m", MaisonDeDisque.class ).getResultList();

		System.out.println( "Found " + maisons.size() + " maisons" );
		Assert.assertEquals( 1, maisons.size() );
		Assert.assertEquals( "TotoRecords", maisons.get( 0 ).getNom() );
	}

	private void startTransaction() throws Exception
	{
		utx.begin();
		em.joinTransaction();
	}
}
