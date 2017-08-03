package fr.lteconsulting.training.javaee.discogs;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

public interface DiscogsWebService
{
	static final String KEY = DiscogsSecrets.getKey();
	static final String SECRET = DiscogsSecrets.getSecret();

	@GET
	@Path( "/database/search" )
	DiscogsArtistSearchResponse searchArtist( @QueryParam( "q" ) String term, @QueryParam( "type" ) String mustBeArtist, @QueryParam( "key" ) String key, @QueryParam( "secret" ) String secret, @HeaderParam( "User-Agent" ) String userAgent );

	@GET
	@Path( "/artists/{id}" )
	DiscogsArtistResponse getArtist( @PathParam( "id" ) int id, @HeaderParam( "User-Agent" ) String userAgent );

	@GET
	@Path( "/artists/{id}/releases" )
	DiscogsArtistReleasesResponse getArtistReleases( @PathParam( "id" ) int id, @HeaderParam( "User-Agent" ) String userAgent );
	
	@GET
	@Path("/releases/{id}")
	DiscogsReleaseResponse getRelease( @PathParam( "id" ) int id, @HeaderParam( "User-Agent" ) String userAgent );
}
