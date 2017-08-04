package fr.lteconsulting.training.javaee.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DiscogsTrack
{
	private String title;
	private String position;
	private String duration;

	public String getTitle()
	{
		return title;
	}

	public void setTitle( String title )
	{
		this.title = title;
	}

	public String getPosition()
	{
		return position;
	}

	public void setPosition( String position )
	{
		this.position = position;
	}

	public String getDuration()
	{
		return duration;
	}

	public void setDuration( String duration )
	{
		this.duration = duration;
	}

	@Override
	public String toString()
	{
		return "DiscogsTrack [position=" + position + ", title=" + title + ", duration=" + duration + "]";
	}
}
