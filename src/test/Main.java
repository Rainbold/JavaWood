package test;

import gestion.algorithme.Algorithme;
import gestion.bois.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Main {
	
	public void main(String[] args) {

		System.out.println("Test du logiciel");
		
		System.out.print("Creation des HashSet : ");
		Set<Planche> planches = new HashSet<Planche>();
		Set<Commande> commandes = new HashSet<Commande>();
		System.out.println("OK");
		
		System.out.print("Creation de deux objets Planche p1 et p2 (id 1 et 2) : ");
		Planche p1 = new Planche(1, 100, 100, 3);
		Planche p2 = new Planche(2, 10, 10, 3);
		System.out.println("OK");
		 
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
		System.out.println(" : OK");
		
		System.out.print("Creation de deux objets Commande c1 et c2 (id 1 et 2) : ");
		Commande c1 = new Commande(4, 10, 10, 2, false);
		Commande c2 = new Commande(3, 100, 10, 2, false);
		Commande c3 = new Commande(2, 40, 10, 2, false);
		Commande c4 = new Commande(5, 50, 10, 2, false);
		System.out.println("OK");
		
		System.out.print("Ajout de c1 deux fois et de c2 dans la collection : ");
		commandes.add(c1);
		commandes.add(c1);
		commandes.add(c2);
		commandes.add(c3);
		commandes.add(c4);
		System.out.println("OK");
		
		// POUR MAXIME 
		List<Commande> l = new ArrayList<>(commandes); // 1/ on tranforme le HashSet en List
		
		Collections.sort(l,new Comparator<Commande>(){ // 2/ on le trie en impl�mentant la fonction directement
			   @Override
			   public int compare(final Commande m1, Commande p1) { // si neg doit �tre devant pos  return -1 (inversement c'est 1)
				     if(m1.getLongueur() >= p1.getLongueur())
				    	 return -1;
				     else
				    	 return 1;
			     }
			 } );
		
		System.out.print("Verification des ids contenus dans la collection commandes :");
		Iterator<Commande> i2 = l.iterator();
		while(i2.hasNext()) {
			 Commande e = i2.next();
			 System.out.print(" " + e.getLongueur());
		}
		System.out.println(" : OK");
		

		System.out.println("----------\nTest de la serialisation : ");
		
		System.out.print("Cr�ation d'une commande avec 3 d�coupes : ");
		Commande c5 = new Commande(6, 100, 50, 2, false);
		Decoupe d1 = new Decoupe(0, 0, 1);
		Decoupe d2 = new Decoupe(0, 25, 2);
		Decoupe d3 = new Decoupe(0, 50, 3);
		c5.getDecoupes().add(d1);
		c5.getDecoupes().add(d2);
		c5.getDecoupes().add(d3);
		commandes.add(c5);
		System.out.println("OK");
		
		Algorithme.serialisation("test.svg", 200, 100, commandes);
		
		System.out.println("\nSerialisation : OK");
		
		
		
		System.out.println("Fin des tests.");

	}

}