package fr.lteconsulting.training.javaee.discogs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DiscogsReleaseResponse
{
	private int id;
	private String title;
	private List<DiscogsTrack> tracklist;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public List<DiscogsTrack> getTracklist()
	{
		return tracklist;
	}

	public void setTracklist( List<DiscogsTrack> tracklist )
	{
		this.tracklist = tracklist;
	}

	@Override
	public String toString()
	{
		return "DiscogsReleaseResponse [id=" + id + ", title=" + title + ", tracklist=" + tracklist + "]";
	}
}
