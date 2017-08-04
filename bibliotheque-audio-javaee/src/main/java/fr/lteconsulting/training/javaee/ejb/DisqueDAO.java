package fr.lteconsulting.training.javaee.ejb;

import javax.ejb.Stateless;

import fr.lteconsulting.training.javaee.entity.Disque;

@Stateless
public class DisqueDAO extends GenericDAO<Disque>
{
	public DisqueDAO()
	{
		super( Disque.class );
	}
}
