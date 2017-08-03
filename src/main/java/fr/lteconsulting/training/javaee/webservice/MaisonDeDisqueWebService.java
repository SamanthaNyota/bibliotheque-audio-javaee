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

import fr.lteconsulting.training.javaee.dto.MaisonDeDisqueDTO;
import fr.lteconsulting.training.javaee.ejb.MaisonDeDisqueDAO;
import fr.lteconsulting.training.javaee.entity.MaisonDeDisque;

@Path( "/maisons" )
public class MaisonDeDisqueWebService
{
	@EJB
	private MaisonDeDisqueDAO maisonDeDisqueDAO;

	@GET
	@Path( "/{id}" )
	@Produces( MediaType.APPLICATION_JSON )
	public MaisonDeDisqueDTO findById( @PathParam( "id" ) int id )
	{
		MaisonDeDisque maison = maisonDeDisqueDAO.findById( id );
		if( maison == null )
			return null;

		return dtoFromEntity( maison );
	}

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<MaisonDeDisqueDTO> findAll()
	{
		List<MaisonDeDisque> maisons = maisonDeDisqueDAO.findAll();
		if( maisons == null )
			return null;

		List<MaisonDeDisqueDTO> dtos = new ArrayList<>();

		for( MaisonDeDisque maison : maisons )
			dtos.add( dtoFromEntity( maison ) );

		return dtos;
	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public MaisonDeDisqueDTO create( MaisonDeDisqueDTO maison )
	{
		MaisonDeDisque maisonEntity = new MaisonDeDisque();
		maisonEntity.setNom( maison.getNom() );

		maisonDeDisqueDAO.ajouter( maisonEntity );

		maison.setId( maisonEntity.getId() );

		return maison;
	}

	@PUT
	@Path( "/{id}" )
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public MaisonDeDisqueDTO update( @PathParam( "id" ) int id, MaisonDeDisqueDTO maison )
	{
		if( id != maison.getId() )
			throw new IllegalArgumentException();

		MaisonDeDisque maisonEntity = new MaisonDeDisque();
		maisonEntity.setId( maison.getId() );
		maisonEntity.setNom( maison.getNom() );

		maisonDeDisqueDAO.update( maisonEntity );

		return maison;
	}

	@DELETE
	@Path( "/{id}" )
	public void delete( @PathParam( "id" ) int id )
	{
		maisonDeDisqueDAO.deleteById( id );
	}

	private MaisonDeDisqueDTO dtoFromEntity( MaisonDeDisque maison )
	{
		if( maison == null )
			return null;

		MaisonDeDisqueDTO dto = new MaisonDeDisqueDTO();

		dto.setId( maison.getId() );
		dto.setNom( maison.getNom() );

		return dto;
	}
}
