package fr.lteconsulting.training.javaee.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.lteconsulting.training.javaee.discogs.DiscogsArtist;
import fr.lteconsulting.training.javaee.dto.ImportedDiscogsArtist;
import fr.lteconsulting.training.javaee.dto.PossibleDiscogsArtistImport;
import fr.lteconsulting.training.javaee.ejb.DiscogsImportation;

@Path( "/discogs" )
public class DiscogsImportationWebService
{
	@EJB
	private DiscogsImportation importation;

	@GET
	@Path( "/searchArtists" )
	@Produces( MediaType.APPLICATION_JSON )
	public List<PossibleDiscogsArtistImport> findById( @QueryParam( "q" ) String searchTerm )
	{
		List<PossibleDiscogsArtistImport> results = new ArrayList<>();

		List<DiscogsArtist> artists = importation.searchArtists( searchTerm );
		if( artists != null )
		{
			for( DiscogsArtist artist : artists )
				results.add( new PossibleDiscogsArtistImport( artist.getId(), artist.getTitle() ) );
		}

		return results;
	}

	@GET
	@Path( "/importArtist" )
	@Produces( MediaType.APPLICATION_JSON )
	public ImportedDiscogsArtist importArtist( @QueryParam( "discogsArtistId" ) int id )
	{
		Integer auteurId = importation.importArtist( id );
		if( auteurId == null )
			return null;

		return new ImportedDiscogsArtist( auteurId, id );
	}
}
