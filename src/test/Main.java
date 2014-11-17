package test;

import gestion.bois.*;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Main {
	
	public static void main(String[] args) {

		Set<Planche> planches = new HashSet<Planche>();
		Set<Commande> commandes = new HashSet<Commande>();
		
		Planche p = new Planche(1, 10, 10, 3);
		Planche p2 = new Planche(1, 10, 10, 3);
		Planche p3 = new Planche(2, 10, 10, 3);
		
		planches.add(p);
		planches.add(p2);
		planches.add(p3);
		
		Iterator<Planche> i = planches.iterator();
		
		while(i.hasNext()) {
			 Planche e = i.next();
			 System.out.println(e.getId());
		}
		
		System.out.println("Commande:");
		
		Commande c = new Commande(1, 10, 10, 2, false);
		Commande c1 = new Commande(2, 10, 10, 2, false);
		
		commandes.add(c);
		commandes.add(c);
		commandes.add(c1);
		
		Iterator<Commande> i2 = commandes.iterator();
		
		while(i2.hasNext()) {
			 Commande e = i2.next();
			 System.out.println(e.getId());
		}
		
		System.out.println("Ferme la ! 2.0");

	}

}
