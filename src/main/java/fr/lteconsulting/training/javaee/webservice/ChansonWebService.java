package fr.lteconsulting.training.javaee.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.lteconsulting.training.javaee.dto.ChansonDTO;
import fr.lteconsulting.training.javaee.ejb.ChansonDAO;
import fr.lteconsulting.training.javaee.entity.Chanson;

@Path( "/chansons" )
public class ChansonWebService
{
	@EJB
	private ChansonDAO chansonDAO;

	@GET
	@Path( "/{id}" )
	@Produces( MediaType.APPLICATION_JSON )
	public ChansonDTO findById( @PathParam( "id" ) int id )
	{
		Chanson chanson = chansonDAO.findById( id );
		if( chanson == null )
			return null;

		return dtoFromEntity( chanson );
	}

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<ChansonDTO> findAll()
	{
		List<Chanson> chansons = chansonDAO.findAll();
		if( chansons == null )
			return null;

		List<ChansonDTO> dtos = new ArrayList<>();

		for( Chanson chanson : chansons )
			dtos.add( dtoFromEntity( chanson ) );

		return dtos;
	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public ChansonDTO create( ChansonDTO chanson )
	{
		Chanson entity = new Chanson();
		entity.setNom( chanson.getNom() );

		chansonDAO.ajouter( entity );

		chanson.setId( entity.getId() );

		return chanson;
	}

	@PUT
	@Path( "/{id}" )
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public ChansonDTO update( @PathParam( "id" ) int id, ChansonDTO chanson )
	{
		if( id != chanson.getId() )
			throw new IllegalArgumentException();

		Chanson entity = new Chanson();
		entity.setId( chanson.getId() );
		entity.setNom( chanson.getNom() );

		chansonDAO.update( entity );

		return chanson;
	}

	@DELETE
	@Path( "/{id}" )
	public void delete( @PathParam( "id" ) int id )
	{
		chansonDAO.deleteById( id );
	}

	private ChansonDTO dtoFromEntity( Chanson chanson )
	{
		if( chanson == null )
			return null;

		ChansonDTO dto = new ChansonDTO();

		dto.setId( chanson.getId() );
		dto.setNom( chanson.getNom() );

		return dto;
	}
}
