package fr.lteconsulting.training.javaee.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Chanson
{
	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	private Disque disque;

	@ManyToOne
	private Auteur auteur;

	private String nom;

	private int duree;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public Disque getDisque()
	{
		return disque;
	}

	public void setDisque( Disque disque )
	{
		this.disque = disque;
	}

	public Auteur getAuteur()
	{
		return auteur;
	}

	public void setAuteur( Auteur auteur )
	{
		this.auteur = auteur;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom( String nom )
	{
		this.nom = nom;
	}

	public int getDuree()
	{
		return duree;
	}

	public void setDuree( int duree )
	{
		this.duree = duree;
	}
}
