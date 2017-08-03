package fr.lteconsulting.training.javaee.discogs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DiscogsArtistReleasesResponse
{
	private List<DiscogsRelease> releases;

	public List<DiscogsRelease> getReleases()
	{
		return releases;
	}

	public void setReleases( List<DiscogsRelease> releases )
	{
		this.releases = releases;
	}

	@Override
	public String toString()
	{
		return "DiscogsArtistReleasesResponse [releases=" + releases + "]";
	}
}
