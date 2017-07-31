package fr.lteconsulting.training.javaee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Auteur
{
	@Id
	@GeneratedValue( strategy = GenerationType.TABLE )
	private int id;

	@ManyToOne
	private MaisonDeDisque maison;

	private String nom;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom( String nom )
	{
		this.nom = nom;
	}

	public MaisonDeDisque getMaison()
	{
		return maison;
	}

	public void setMaison( MaisonDeDisque maison )
	{
		this.maison = maison;
	}
}
