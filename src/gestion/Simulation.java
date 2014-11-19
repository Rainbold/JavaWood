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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Ferme la !");
	}

}
