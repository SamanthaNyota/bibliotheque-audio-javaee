package fr.lteconsulting.training.javaee.discogs;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

public interface DiscogsWebService
{
	static final String KEY = DiscogsSecrets.getKey();
	static final String SECRET = DiscogsSecrets.getSecret();

	@GET
	@Path( "/database/search" )
	ArtistSearchResponse searchArtist( @QueryParam( "q" ) String term, @QueryParam( "type" ) String mustBeArtist, @QueryParam( "key" ) String key, @QueryParam( "secret" ) String secret, @HeaderParam( "User-Agent" ) String userAgent );
}
