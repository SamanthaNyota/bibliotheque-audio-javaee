# TP Java EE

## Exercice

### 1. Mettre en place Arquillian sur votre projet

Inspirez vous du commit [5ce59](https://github.com/ltearno/bibliotheque-audio-javaee/commit/5ce5922725a276180c12c8b99312330fec7374b1)

### 2. Implémenter les tests des DAO

Vérifier que les DAO remplissent bien leur rôle en contrôlant les opérations CRUD qu'ils sont censés réaliser.

#### Exemples :

Vérifier qu'on ne peut effacer une MaisonDeDisque si des Auteurs la référence...

Autre exemple de code d'une méthode de test :

	MaisonDeDisque maison = new MaisonDeDisque();
	maison.setNom("Toto");
	dao.ajouter(maison);
	
	Assert.assertTrue("Le DAO n'a pas renseigné l'id généré", maison.getId() > 0);
	
	// Note : pour que cette ligne fonctionne il faut implémenter les méthodes `equals()` et `hashCode()` sur la classe `MaisonDeDisque`.
	Assert.assertEquals("Le DAO n'a pas enregistré correctement l'entité", maison, dao.findById(maison.getId()));

### 3. Utilisation d'un service REST externe pour alimenter notre base de Disques

Il est maintenant l'heure de compléter le projet pour avoir les DAO manquants (`DisqueDAO` et `ChansonDAO`).

Une fois ceci fait, testez ceux-ci comme précédemment.

#### Le service REST du catalogue de Disques et Chansons

Suivez les instructions sur cette page : https://www.discogs.com/developers/ pour comprendre comment fonctionne leur service rest.

Vous pouvez essayer cette requête dans PostMan : GET https://api.discogs.com/artists/1/releases?page=2&per_page=75

Mettez en place le client REST pour ce service (définition de l'interface, des DTOs etc).

Testez le client.

Maintenant essayez d'intégrer ce client avec votre projet. L'idée est d'avoir la possibilité d'alimenter notre base de données avec les données de `Discogs`.

#### Mode d'emploi de Discogs

Créer un compte avec la page https://www.discogs.com/fr/users/create

Aller dans les developper settings ici : https://www.discogs.com/fr/settings/developers

Cliquer sur "Créer une application"

Remplir le nom, la description, et cliquer sur le bouton pour valider.

Bien mémoriser les données sur cette page (ConsumerKey, ConsumerSecret, ...)

**ATTENTION NE PAS METTRE LE CONSUMER SECRET SUR GITHUB, C'EST VOTRE CLEF PRIVEE**

A partir de la chacune de vos requêtes doivent inclure deux paramètres supplémentaire :

- `key` : à valoriser avec votre *ConsumerKey*,
- `secret` à vaoriser avec votre *ConsumerSecret*.

Ensuite cette requête doit fonctionner (elle cherche les artistes nommés "Toto") :

`https://api.discogs.com/database/search?q=Toto&type=artist&key=VOTRE_KEY&secret=VOTRE_SECRET`

### 4. Importation des données de Discogs

Une fois testé l'importation depuis Discogs, nous allons rendre cette fonctionnalité accessible à nos utilisateurs.

Fournissez un Web Service supplémentaire, lié à l'adresse '/api/discogs'.

Pour ceci, créez une classe `@Path("/discogs") public class DiscogsImportationWebService`.

Cette classe aura deux méthodes, dont l'exécution est déclenché par la réception d'une requête HTTP (que pour l'instant vous initierez à partir du navigateur ou de post man). Plus tard, ce WebService sera utilisé par la partie Interface Graphique de notre application.

Ce WebService devra répondre aux requetes suivantes :

- `http://localhost:8080/bibliotheque-audio-javaee/api/discogs/searchArtists?q=NOM_ARTISTE`
- `http://localhost:8080/bibliotheque-audio-javaee/api/discogs/importArtist?discogArtistId=ID_ARTISTE_CHEZ_DISCOGS`

#### Première requête

Lorsque l'utilisateur envoie une reuête `http://localhost:8080/bibliotheque-audio-javaee/api/discogs/searchArtists?q=NOM_ARTISTE`, notre serveur devra renvoyer (en JSON) la liste des couples { "discogs_id": identifiantDiscogs, "nom_artiste": nomDeLArtiste}.

Ceci permettra à l'utilisateur de demander la liste des artistes qu'il peut importer en fonction de ceux disponibles chez Discogs.

#### Deuxieme requête

La deuxieme requête déclenche réellement l'importation d'un artiste. Dans l'implémentation de cette méthode, il vous faudra appeler le WS Discogs et importer l'artiste, ses disques ainsi que les chansons s'y trouvant.

#### Validation

Lancer le serveur Wildfly avec votre application déployée, et vérifier que le nouveau WS fonctionne correctement. 

### 5. Faire une petite UI

Par exemple une petite application Angular 2 qui utilise notre service rest.

### 6. Utiliser une DS vers MySQL

La DS utilisée par le contexte de persistence (de test) dans le projet tel qu'il est est nommée `ExempleDS`. Il s'agit d'une DS de démonstration 
présente dans l'archive de WildFly. Cette DS utilise une base de données H2, qui n'est donc pas **iso-prod**.

Changer le projet afin d'utiliser une DS que vous créez, qui s'appellera `BibliothequeTestDS`. La DS est à injecter dans le Wildfly téléchargé et déployé par Arquillian.

Suivez (et adaptez) les instructions données [ici](http://arquillian.org/guides/testing_java_persistence/#run_the_test_on_jboss_as_7).