package gestion;

import gestion.algorithme.Algorithme;
import gestion.bois.*;

import java.io.FileNotFoundException;
import java.util.*;

import javax.xml.stream.XMLStreamException;

public class Simulation {

	public static void main(String[] args) {

		Set<Planche> planches = new HashSet<Planche>();
		Set<Commande> commandes = new HashSet<Commande>();
		
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
		System.out.println(" : OK");
		
		System.out.println(planches.toString());
		
		System.out.println("Ferme la !");
	}

}
