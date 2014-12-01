package gestion;

import gestion.algorithme.*;
import gestion.bois.*;

import java.io.FileNotFoundException;
import java.util.*;

import javax.xml.stream.XMLStreamException;

public class Simulation {

	public static void main(String[] args) {
		System.out.println("Debut de la simulation");
		
		Set<Planche> planches = new HashSet<Planche>();
		Set<Commande> commandes = new HashSet<Commande>();
		
		System.out.println("Lecture des fichiers...");
		try {
			Algorithme.XMLParseFournisseur("fournisseurs.xml", planches);
			Algorithme.XMLParseCommande("commandes.xml", commandes);
		} catch (FileNotFoundException | XMLStreamException e) {
			e.printStackTrace();
		}
		System.out.println("Calculs...");
		Methode1.process(commandes, planches);
		Methode2.process(commandes, planches);
		System.out.println("Tri des prix...");
		Algorithme.sortPrix(planches.size());
		System.out.println("Fin de la simulation.");
	}
}
