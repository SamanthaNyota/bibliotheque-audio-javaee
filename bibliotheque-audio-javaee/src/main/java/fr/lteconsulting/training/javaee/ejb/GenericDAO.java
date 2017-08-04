package fr.lteconsulting.training.javaee.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public abstract class GenericDAO<T>
{
	private final Class<T> clazz;

	protected GenericDAO( Class<T> clazz )
	{
		this.clazz = clazz;
	}

	@PersistenceContext( name = "Bibliotheque" )
	protected EntityManager em;

	public T findById( int id )
	{
		return em.find( clazz, id );
	}

	public List<T> findAll()
	{
		return em.createQuery( "from " + clazz.getSimpleName() + " entity", clazz ).getResultList();
	}

	public void ajouter( T entity )
	{
		em.persist( entity );
	}

	public void update( T entity )
	{
		em.merge( entity );
	}

	public void deleteById( int id )
	{
		T entity = em.find( clazz, id );
		delete( entity );
	}

	public void delete( T entity )
	{
		em.remove( entity );
	}
}
