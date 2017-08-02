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

import fr.lteconsulting.training.javaee.dto.DisqueDTO;
import fr.lteconsulting.training.javaee.ejb.DisqueDAO;
import fr.lteconsulting.training.javaee.entity.Disque;

@Path( "/disques" )
public class DisqueWebService
{
	@EJB
	private DisqueDAO disqueDAO;

	@GET
	@Path( "/{id}" )
	@Produces( MediaType.APPLICATION_JSON )
	public DisqueDTO findById( @PathParam( "id" ) int id )
	{
		Disque disque = disqueDAO.findById( id );
		if( disque == null )
			return null;

		return dtoFromEntity( disque );
	}

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<DisqueDTO> findAll()
	{
		List<Disque> disques = disqueDAO.findAll();
		if( disques == null )
			return null;

		List<DisqueDTO> dtos = new ArrayList<>();

		for( Disque disque : disques )
			dtos.add( dtoFromEntity( disque ) );

		return dtos;
	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public DisqueDTO create( DisqueDTO disque )
	{
		Disque entity = new Disque();
		entity.setNom( disque.getNom() );

		disqueDAO.ajouter( entity );

		disque.setId( entity.getId() );

		return disque;
	}

	@PUT
	@Path( "/{id}" )
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public DisqueDTO update( @PathParam( "id" ) int id, DisqueDTO disque )
	{
		if( id != disque.getId() )
			throw new IllegalArgumentException();

		Disque entity = new Disque();
		entity.setId( disque.getId() );
		entity.setNom( disque.getNom() );

		disqueDAO.update( entity );

		return disque;
	}

	@DELETE
	@Path( "/{id}" )
	public void delete( @PathParam( "id" ) int id )
	{
		disqueDAO.deleteById( id );
	}

	private DisqueDTO dtoFromEntity( Disque disque )
	{
		if( disque == null )
			return null;

		DisqueDTO dto = new DisqueDTO();

		dto.setId( disque.getId() );
		dto.setNom( disque.getNom() );

		return dto;
	}
}
