package fr.lteconsulting.training.javaee.dto;

public class AuteurDTO
{
	private int id;
	private String nom;
	private int maisonDeDisqueId;

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

	public int getMaisonDeDisqueId()
	{
		return maisonDeDisqueId;
	}

	public void setMaisonDeDisqueId( int maisonDeDisqueId )
	{
		this.maisonDeDisqueId = maisonDeDisqueId;
	}
}
