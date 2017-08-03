package fr.lteconsulting.training.javaee.discogs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DiscogsArtistResponse
{
	private int id;
	private String name;
	private List<String> namevariations;
	private String profile;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public List<String> getNamevariations()
	{
		return namevariations;
	}

	public void setNamevariations( List<String> namevariations )
	{
		this.namevariations = namevariations;
	}

	public String getProfile()
	{
		return profile;
	}

	public void setProfile( String profile )
	{
		this.profile = profile;
	}

	@Override
	public String toString()
	{
		return "DiscogsArtistResponse [id=" + id + ", namevariations=" + namevariations + ", profile=" + profile + "]";
	}
}
