package fr.lteconsulting.training.javaee.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Auteur
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Integer id;

	@ManyToOne
	private MaisonDeDisque maison;

	@OneToMany( mappedBy = "auteur" )
	private List<Chanson> chansons = new ArrayList<>();

	private String nom;

	public Integer getId()
	{
		return id;
	}

	public void setId( Integer id )
	{
		this.id = id;
	}

	public String getNom()
	{
		return nom;
	}

	public void setNom( String nom )
	{
		this.nom = nom;
	}

	public MaisonDeDisque getMaison()
	{
		return maison;
	}

	public void setMaison( MaisonDeDisque maison )
	{
		this.maison = maison;
	}

	public List<Chanson> getChansons()
	{
		return chansons;
	}

	public void setChansons( List<Chanson> chansons )
	{
		this.chansons = chansons;
	}
}
