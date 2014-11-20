package gestion;

import gestion.algorithme.Algorithme;
import gestion.bois.*;

import java.io.FileNotFoundException;
import java.util.*;

import javax.xml.stream.XMLStreamException;

public class Simulation {

	public static void main(String[] args) {

		HashSet<Planche> planches = new HashSet<Planche>();
		HashSet<Commande> commandes = new HashSet<Commande>();
		
		try {
			Algorithme.XMLParseFournisseur("fournisseurs.xml", planches);
			Algorithme.XMLParseCommande("commandes.xml", commandes);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.print("Verification des ids contenus dans la collection planches :");
		Iterator<Planche> i = planches.iterator();
		while(i.hasNext()) {
			 Planche e = i.next();
			 System.out.print(" " + e.getId());
		}
		
		Algorithme.methode1(commandes, planches);
	}

}
