package fr.lteconsulting.training.javaee.dto;

public class ImportedDiscogsArtist
{
	private int id;
	private int discogsId;

	public ImportedDiscogsArtist()
	{
	}

	public ImportedDiscogsArtist( int id, int discogsId )
	{
		this.id = id;
		this.discogsId = discogsId;
	}

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public int getDiscogsId()
	{
		return discogsId;
	}

	public void setDiscogsId( int discogsId )
	{
		this.discogsId = discogsId;
	}
}
