package fr.lteconsulting.training.javaee.dto;

import java.util.List;

public class DisqueDTO
{
	private int id;
	private String nom;
	private List<ChansonDTO> chansons;

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

	public List<ChansonDTO> getChansons()
	{
		return chansons;
	}

	public void setChansons( List<ChansonDTO> chansons )
	{
		this.chansons = chansons;
	}
}
