package fr.lteconsulting.training.javaee.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.lteconsulting.training.javaee.entity.Chanson;

@Stateless
public class ChansonDAO
{
	@PersistenceContext( name = "Bibliotheque" )
	private EntityManager em;

	public Chanson findById( int id )
	{
		return em.find( Chanson.class, id );
	}

	public List<Chanson> findAll()
	{
		TypedQuery<Chanson> query = em.createQuery( "from Chanson c", Chanson.class );
		return query.getResultList();
	}

	public void ajouter( Chanson chanson )
	{
		em.persist( chanson );
	}

	public void update( Chanson chanson )
	{
		em.merge( chanson );
	}

	public void deleteById( int id )
	{
		Chanson chanson = em.find( Chanson.class, id );
		delete( chanson );
	}

	public void delete( Chanson chanson )
	{
		em.remove( chanson );
	}
}
