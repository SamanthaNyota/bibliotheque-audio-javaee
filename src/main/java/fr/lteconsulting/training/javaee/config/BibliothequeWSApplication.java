package fr.lteconsulting.training.javaee.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import fr.lteconsulting.training.javaee.webservice.AuteurWebService;
import fr.lteconsulting.training.javaee.webservice.ChansonWebService;
import fr.lteconsulting.training.javaee.webservice.DiscogsImportationWebService;
import fr.lteconsulting.training.javaee.webservice.DisqueWebService;
import fr.lteconsulting.training.javaee.webservice.MaisonDeDisqueWebService;

@ApplicationPath( "/api" )
public class BibliothequeWSApplication extends Application
{
	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> result = new HashSet<>();
		result.add( MaisonDeDisqueWebService.class );
		result.add( DisqueWebService.class );
		result.add( ChansonWebService.class );
		result.add( AuteurWebService.class );
		result.add( DiscogsImportationWebService.class );
		return result;
	}
}
