package fr.lteconsulting.training.javaee.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import fr.lteconsulting.training.javaee.entity.Disque;

@Stateless
public class DisqueDAO extends GenericDAO<Disque>
{
	public DisqueDAO()
	{
		super( Disque.class );
	}

	public List<Disque> getArtistDisquesWithChansons( int artisteId )
	{
		TypedQuery<Disque> query = em.createQuery( "select distinct d from Disque d left join fetch d.chansons c left join c.auteur where c.auteur.id=:id", Disque.class );
		query.setParameter( "id", artisteId );
		List<Disque> disques = query.getResultList();
		return disques;
	}
}
