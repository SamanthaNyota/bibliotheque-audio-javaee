package fr.lteconsulting.training.javaee.ejb;

import javax.ejb.Stateless;

import fr.lteconsulting.training.javaee.entity.Auteur;

@Stateless
public class AuteurDAO extends GenericDAO<Auteur>
{
	public AuteurDAO()
	{
		super( Auteur.class );
	}
}
