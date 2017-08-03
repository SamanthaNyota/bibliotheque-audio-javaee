package fr.lteconsulting.training.javaee.ejb;

import javax.ejb.Stateless;

import fr.lteconsulting.training.javaee.entity.Chanson;

@Stateless
public class ChansonDAO extends GenericDAO<Chanson>
{
	public ChansonDAO()
	{
		super( Chanson.class );
	}
}
