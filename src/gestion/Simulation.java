package gestion;

import gestion.algorithme.*;
import gestion.bois.*;

import java.io.FileNotFoundException;
import java.util.*;

import javax.xml.stream.XMLStreamException;

public class Simulation {

	public static void main(String[] args) {

		Set<Planche> planches = new HashSet<Planche>();
		Set<Commande> commandes = new HashSet<Commande>();
		
		try {
			// Converts XML text into an XML document object
			Algorithme.XMLParseFournisseur("fournisseurs.xml", planches);
			Algorithme.XMLParseCommande("commandes.xml", commandes);
		} catch (FileNotFoundException | XMLStreamException e) {
			
			e.printStackTrace();
		}
		
		Methode1.process(commandes, planches);
		Methode2.process(commandes, planches);
		Algorithme.sortPrix(planches.size());
	}
}
