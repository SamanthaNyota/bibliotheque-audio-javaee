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

import fr.lteconsulting.training.javaee.dto.AuteurDTO;
import fr.lteconsulting.training.javaee.dto.ChansonDTO;
import fr.lteconsulting.training.javaee.dto.DisqueDTO;
import fr.lteconsulting.training.javaee.ejb.AuteurDAO;
import fr.lteconsulting.training.javaee.ejb.DisqueDAO;
import fr.lteconsulting.training.javaee.ejb.MaisonDeDisqueDAO;
import fr.lteconsulting.training.javaee.entity.Auteur;
import fr.lteconsulting.training.javaee.entity.Chanson;
import fr.lteconsulting.training.javaee.entity.Disque;

@Path( "/auteurs" )
public class AuteurWebService
{
	@EJB
	private AuteurDAO auteurDAO;

	@EJB
	private DisqueDAO disqueDAO;

	@EJB
	private MaisonDeDisqueDAO maisonDeDisqueDAO;

	@GET
	@Path( "/{id}" )
	@Produces( MediaType.APPLICATION_JSON )
	public AuteurDTO findById( @PathParam( "id" ) int id )
	{
		Auteur auteur = auteurDAO.findById( id );
		if( auteur == null )
			return null;

		return dtoFromEntity( auteur );
	}

	@GET
	@Produces( MediaType.APPLICATION_JSON )
	public List<AuteurDTO> findAll()
	{
		List<Auteur> auteurs = auteurDAO.findAll();
		if( auteurs == null )
			return null;

		List<AuteurDTO> dtos = new ArrayList<>();

		for( Auteur auteur : auteurs )
			dtos.add( dtoFromEntity( auteur ) );

		return dtos;
	}

	@GET
	@Path( "/{id}/disques" )
	@Produces( MediaType.APPLICATION_JSON )
	public List<DisqueDTO> getArtistDisques( @PathParam( "id" ) int id )
	{
		List<DisqueDTO> results = new ArrayList<>();

		List<Disque> disques = disqueDAO.getArtistDisquesWithChansons( id );
		if( disques != null )
		{
			for( Disque disque : disques )
				results.add( dtoFromEntity( disque ) );
		}

		return results;
	}

	@POST
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public AuteurDTO create( AuteurDTO auteur )
	{
		Auteur entity = new Auteur();
		entity.setNom( auteur.getNom() );

		if( auteur.getMaisonDeDisqueId() > 0 )
			entity.setMaison( maisonDeDisqueDAO.findById( auteur.getMaisonDeDisqueId() ) );

		auteurDAO.ajouter( entity );

		auteur.setId( entity.getId() );

		return auteur;
	}

	@PUT
	@Path( "/{id}" )
	@Consumes( MediaType.APPLICATION_JSON )
	@Produces( MediaType.APPLICATION_JSON )
	public AuteurDTO update( @PathParam( "id" ) int id, AuteurDTO auteur )
	{
		if( id != auteur.getId() )
			throw new IllegalArgumentException();

		Auteur entity = new Auteur();
		entity.setId( auteur.getId() );
		entity.setNom( auteur.getNom() );

		auteurDAO.update( entity );

		return auteur;
	}

	@DELETE
	@Path( "/{id}" )
	public void delete( @PathParam( "id" ) int id )
	{
		auteurDAO.deleteById( id );
	}

	private AuteurDTO dtoFromEntity( Auteur auteur )
	{
		if( auteur == null )
			return null;

		AuteurDTO dto = new AuteurDTO();

		dto.setId( auteur.getId() );
		dto.setNom( auteur.getNom() );

		return dto;
	}

	/**
	 * Rempli un DisqueDTO a partir d'un Disque.
	 * 
	 * Rempli également les chansons du disque de façon à n'avoir pas trop de requêtes à faire côté client
	 * 
	 * @param disque
	 * @return
	 */
	private DisqueDTO dtoFromEntity( Disque disque )
	{
		if( disque == null )
			return null;

		DisqueDTO dto = new DisqueDTO();

		dto.setId( disque.getId() );
		dto.setNom( disque.getNom() );

		if( disque.getChansons() != null )
		{
			dto.setChansons( new ArrayList<>() );

			for( Chanson chanson : disque.getChansons() )
			{
				ChansonDTO chansonDto = new ChansonDTO();

				chansonDto.setId( chanson.getId() );
				chansonDto.setNom( chanson.getNom() );

				dto.getChansons().add( chansonDto );
			}
		}

		return dto;
	}
}
