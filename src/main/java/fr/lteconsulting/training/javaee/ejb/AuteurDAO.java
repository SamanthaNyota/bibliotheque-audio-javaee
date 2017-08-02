package fr.lteconsulting.training.javaee.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fr.lteconsulting.training.javaee.entity.Auteur;

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
		return em.createQuery( "from Auteur a", Auteur.class ).getResultList();
	}

	public void ajouter( Auteur auteur )
	{
		em.persist( auteur );
	}

	public void update( Auteur auteur )
	{
		em.merge( auteur );
	}

	public void deleteById( int id )
	{
		Auteur auteur = em.find( Auteur.class, id );
		delete( auteur );
	}

	public void delete( Auteur auteur )
	{
		em.remove( auteur );
	}
}
