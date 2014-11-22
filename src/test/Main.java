package test;

import gestion.algorithme.*;
import gestion.bois.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {
	
	public static void main(String[] args) {

		System.out.println("Test du logiciel\n");
		
		System.out.println("Creation des HashSet :");
		System.out.print("planches : HashSet<planche> : ");
		Set<Planche> planches = new HashSet<Planche>();
		System.out.println("OK");
		System.out.print("commandes : HashSet<commande> : ");
		Set<Commande> commandes = new HashSet<Commande>();
		System.out.println("OK\n");
		
		System.out.println("Creation de deux objets Planche :");
		System.out.print("Planche p1 id=1 longueur=400 largeur=200 prix=3 : ");
		Planche p1 = new Planche(1, 400, 200, 3);
		System.out.println("OK");
		System.out.print("Planche p1 id=2 longueur=300 largeur=300 prix=3 : ");
		Planche p2 = new Planche(2, 400, 300, 3);
		System.out.println("OK\n");
		 
		System.out.print("Ajout de p1 deux fois et de p2 dans la collection : ");
		planches.add(p1);
		planches.add(p1);
		planches.add(p2);
		System.out.println("OK");		
		
		System.out.print("Verification des ids contenus dans la collection planches :");
		Iterator<Planche> i = planches.iterator();
		while(i.hasNext()) {
			 Planche e = i.next();
			 System.out.print(" " + e.getId());
		}
		System.out.println(" : OK\n");
		
		System.out.print("Creation de d'objets Commande : ");
		System.out.print("Commande c1 id=1 longueur=200 largeur=100 quantite=3 : ");
		Commande c1 = new Commande(1, 200, 100, 3);
		System.out.println("OK");
		System.out.print("Commande c2 id=2 longueur=250 largeur=250 quantite=3 : ");
		Commande c2 = new Commande(2, 250, 150, 3);
		System.out.println("OK");
		System.out.print("Commande c3 id=3 longueur=250 largeur=250 quantite=3 REJETE: ");
		Commande c3 = new Commande(3, 250, 150, 3);
		c3.setRejet(true);
		System.out.println("OK\n");
		
		System.out.print("Creation d'objets Decoupe : ");
		System.out.print("Decoupe d1 x=0 y=0 idPlanche=1 idCommande=2 : ");
		Decoupe d1 = new Decoupe(0, 0, 1, 2);
		System.out.println("OK");
		System.out.print("Decoupe d2 x=100 y=100 idPlanche=2 idCommande=2 : ");
		Decoupe d2 = new Decoupe(0, 100, 2, 2);
		System.out.println("OK");
		System.out.print("Decoupe d1 x=200 y=100 idPlanche=3 idCommande=1 : ");
		Decoupe d3 = new Decoupe(50, 100, 3, 1);
		System.out.println("OK\n");
		
		System.out.println("Ajout des decoupes dans les commandes :\nc1 add d3\nc2 add d2\nc2 add d1");
		c1.getDecoupes().add(d3);
		c2.getDecoupes().add(d2);
		c2.getDecoupes().add(d1);
		System.out.println("OK\n");
		
		System.out.print("Ajout des commandes dans le HashSet :\nAdd c1 - Add c2 - Add c3\n");
		commandes.add(c1);
		commandes.add(c2);
		commandes.add(c3);
		System.out.println("OK\n");
		
		List<Commande> cList = new ArrayList<>(commandes);
		
		System.out.println("----- Test du resultat XML -----");
		System.out.print("Resultat.xml() -> test1.xml : ");
		Resultat.xml("test1.xml", 3, p1, 0, cList);
		System.out.println("OK");
		System.out.print("Resultat.xml() -> test2.xml : ");
		Resultat.xml("test2.xml", 3, p2, 0, cList);
		System.out.println("OK\n");
		
		System.out.println("----- Test du resultat SVG -----");
		System.out.print("Resultat.svg() -> test1.svg : ");
		Resultat.svg("test1.svg", p1, cList);
		System.out.println("OK");
		System.out.print("Resultat.svg() -> test1.svg : ");
		Resultat.svg("test2.svg", p2, cList);
		System.out.println("OK\n");
		
		System.out.println("----- Test de la methode 1 -----");
		System.out.print("Methode1.process() -> Results.*.m1.* : ");
		Methode1.process(commandes, planches);
		System.out.println("OK\n");
		
		//System.out.println("----- Test de la methode 2 -----");
		//System.out.print("Methode2.process() -> Results.*.m2.* : ");
		//Methode2.process(commandes, planches);
		//System.out.println("OK\n");
						
		System.out.println("Fin des tests.");
	}
}
