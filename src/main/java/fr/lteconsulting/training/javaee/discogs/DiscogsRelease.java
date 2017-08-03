package fr.lteconsulting.training.javaee.discogs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties( ignoreUnknown = true )
public class DiscogsRelease
{
	private int id;
	private String title;
	private int year;

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

	public int getYear()
	{
		return year;
	}

	public void setYear( int year )
	{
		this.year = year;
	}

	@Override
	public String toString()
	{
		return "DiscogsRelease [id=" + id + ", title=" + title + ", year=" + year + "]";
	}
}
