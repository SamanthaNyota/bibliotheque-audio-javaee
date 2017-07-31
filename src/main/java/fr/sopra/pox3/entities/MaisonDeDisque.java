package fr.sopra.pox3.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MaisonDeDisque
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private int id;

	@OneToMany( mappedBy = "maison" )
	private List<Auteur> auteurs = new ArrayList<>();

	private String nom;

	public int getId()
	{
		return id;
	}

	public void setId( int id )
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

	public List<Auteur> getAuteurs()
	{
		return auteurs;
	}

	public void addAuteur( Auteur auteur )
	{
		auteurs.add( auteur );
	}
}
