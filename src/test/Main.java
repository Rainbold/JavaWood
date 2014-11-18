package test;

import gestion.bois.*;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Main {
	
	public static void main(String[] args) {

		System.out.println("Test du logiciel");
		
		System.out.print("Creation des HashSet : ");
		Set<Planche> planches = new HashSet<Planche>();
		Set<Commande> commandes = new HashSet<Commande>();
		System.out.println("OK");
		
		System.out.print("Creation de deux objets Planche p1 et p2 (id 1 et 2) : ");
		Planche p1 = new Planche(1, 10, 10, 3);
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
		Commande c1 = new Commande(1, 10, 10, 2, false);
		Commande c2 = new Commande(2, 10, 10, 2, false);
		System.out.println("OK");
		
		System.out.print("Ajout de c1 deux fois et de c2 dans la collection : ");
		commandes.add(c1);
		commandes.add(c1);
		commandes.add(c2);
		System.out.println("OK");
		
		System.out.print("Verification des ids contenus dans la collection commandes :");
		Iterator<Commande> i2 = commandes.iterator();
		while(i2.hasNext()) {
			 Commande e = i2.next();
			 System.out.print(" " + e.getId());
		}
		System.out.println(" : OK");
		
		System.out.println("Fin des tests.");

	}

}
