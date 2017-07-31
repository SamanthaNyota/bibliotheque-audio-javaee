package fr.sopra.pox3.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import fr.sopra.pox3.entities.Auteur;

@Stateless
public class AuteurDAO
{
	@PersistenceContext( name = "Bibliotheque" )
	private EntityManager em;

	public Auteur findById( int id )
	{
		return em.find( Auteur.class, id );
	}

	public List<Auteur> findAll()
	{
		TypedQuery<Auteur> query = em.createQuery( "from fr.sopra.pox3.entities.Auteur a", Auteur.class );
		return query.getResultList();
	}

	public void ajouterAuteur( Auteur auteur )
	{
		em.persist( auteur );
	}

	public void updateAuteur( Auteur auteur )
	{
		em.merge( auteur );
	}

	public void deleteAuteur( Auteur auteur )
	{
		em.remove( auteur );
	}
}
