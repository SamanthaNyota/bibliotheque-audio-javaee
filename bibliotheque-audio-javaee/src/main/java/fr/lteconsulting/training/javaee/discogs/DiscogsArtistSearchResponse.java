package fr.lteconsulting.training.javaee.discogs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DiscogsArtistSearchResponse
{
	private List<DiscogsArtist> results;

	public List<DiscogsArtist> getResults()
	{
		return results;
	}

	public void setResults( List<DiscogsArtist> results )
	{
		this.results = results;
	}
}
