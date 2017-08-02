package fr.lteconsulting.training.javaee.discogs;

/**
 * Pour que ceci fonctionne vous devez :
 * 
 * - soit avoir les variables d'environnement DISCOGS_KEY et DISCOGS_SECRET
 * - soit les définir comme System Property de la JVM en ajoutant les paramètres 
 *   de lancement DISCOGS_KEY et DISCOGS_SECRET ( -DDISCOGS_KEY=VALEUR dans les params du launcher)
 */
public class DiscogsSecrets
{
	private static final String KEY_VARIABLE_NAME = "DISCOGS_KEY";
	private static final String SECRET_VARIABLE_NAME = "DISCOGS_SECRET";

	public static String getKey()
	{
		return getValue( KEY_VARIABLE_NAME );
	}

	public static String getSecret()
	{
		return getValue( SECRET_VARIABLE_NAME );
	}

	private static String getValue( String variableName )
	{
		String value = System.getenv( variableName );
		if( value == null )
			value = System.getProperty( variableName );

		if( value == null )
			throw new RuntimeException( "Vous avez oublié de paramterer "
					+ "l'authent Discogs (svp valoriser la "
					+ "variable d'environnement " + variableName + ")" );

		return value;
	}
}
