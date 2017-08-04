package fr.lteconsulting.training.javaee.ejb;

import javax.ejb.Stateless;

import fr.lteconsulting.training.javaee.entity.MaisonDeDisque;

@Stateless
public class MaisonDeDisqueDAO extends GenericDAO<MaisonDeDisque>
{
	public MaisonDeDisqueDAO()
	{
		super( MaisonDeDisque.class );
	}
}
