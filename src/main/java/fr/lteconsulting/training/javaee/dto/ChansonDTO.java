package fr.lteconsulting.training.javaee.dto;

public class ChansonDTO
{
	private int id;
	private String nom;
	private int duree;
	private int auteurId;

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

	public int getDuree()
	{
		return duree;
	}

	public void setDuree( int duree )
	{
		this.duree = duree;
	}

	public int getAuteurId()
	{
		return auteurId;
	}

	public void setAuteurId( int auteurId )
	{
		this.auteurId = auteurId;
	}
}
