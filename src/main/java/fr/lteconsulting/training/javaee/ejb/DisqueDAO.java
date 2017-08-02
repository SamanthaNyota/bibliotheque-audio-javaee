package fr.lteconsulting.training.javaee.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.lteconsulting.training.javaee.entity.Disque;

@Stateless
public class DisqueDAO
{
	@PersistenceContext( name = "Bibliotheque" )
	private EntityManager em;

	public Disque findById( int id )
	{
		return em.find( Disque.class, id );
	}

	public List<Disque> findAll()
	{
		TypedQuery<Disque> query = em.createQuery( "from Disque d", Disque.class );
		return query.getResultList();
	}

	public void ajouter( Disque disque )
	{
		em.persist( disque );
	}

	public void update( Disque disque )
	{
		em.merge( disque );
	}

	public void deleteById( int id )
	{
		Disque disque = em.find( Disque.class, id );
		delete( disque );
	}

	public void delete( Disque disque )
	{
		em.remove( disque );
	}
}
