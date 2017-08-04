package fr.lteconsulting.training.javaee.dto;

public class PossibleDiscogsArtistImport
{
	private int discogId;
	private String artistName;

	public PossibleDiscogsArtistImport()
	{
	}

	public PossibleDiscogsArtistImport( int discogId, String artistName )
	{
		this.discogId = discogId;
		this.artistName = artistName;
	}

	public int getDiscogId()
	{
		return discogId;
	}

	public void setDiscogId( int discogId )
	{
		this.discogId = discogId;
	}

	public String getArtistName()
	{
		return artistName;
	}

	public void setArtistName( String artistName )
	{
		this.artistName = artistName;
	}
}
