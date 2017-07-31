package fr.sopra.pox3.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.sopra.pox3.entities.MaisonDeDisque;

@Stateless
public class MaisonDeDisqueDAO
{
	@PersistenceContext( name = "Bibliotheque" )
	private EntityManager em;

	public MaisonDeDisque findById( int id )
	{
		TypedQuery<MaisonDeDisque> q = em.createQuery( "from MaisonDeDisque m left join fetch m.auteurs where m.id=:id",
				MaisonDeDisque.class );
		q.setParameter( "id", id );
		return q.getSingleResult();
	}

	public List<MaisonDeDisque> findAll()
	{
		return em.createQuery( "from MaisonDeDisque m", MaisonDeDisque.class ).getResultList();
	}

	public void add( MaisonDeDisque maisonDeDisque )
	{
		em.persist( maisonDeDisque );
	}

	public void update( MaisonDeDisque maisonDeDisque )
	{
		em.merge( maisonDeDisque );
	}

	public void deleteById( int id )
	{
		MaisonDeDisque maison = em.find( MaisonDeDisque.class, id );
		if( maison != null )
			em.remove( maison );
	}
}
