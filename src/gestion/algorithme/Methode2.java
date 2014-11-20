package gestion.algorithme;

import gestion.bois.Commande;
import gestion.bois.Decoupe;
import gestion.bois.Planche;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class Methode2 extends Algorithme {

public static void process(HashSet<Commande> commandes, HashSet<Planche> planches) {
		
		List<Planche> pList = planchesSort(planches);
		List<Commande> cList = commandesSortLargeur(commandes);
		
		int idPlanches = 1;
		int y = 0;
		int x = 0;
		int quantite = 0;
		Vector<Decoupe> decoupes;
		Decoupe d;
		
		Iterator<Planche> pIt = pList.iterator();
		
		while(pIt.hasNext())
		{
			idPlanches = 1;
			y = 0;
			x = 0;
			Planche p = pIt.next();	 
			
			Iterator<Commande> cIterator = cList.iterator();
			Commande c = cIterator.next();
			Commande c2 = c;
			
			while(cIterator.hasNext())
			{
				if(c.getQuantite() == 0){
					c = cIterator.next();
				}
				decoupes = c.getDecoupes();
				quantite = c.getQuantite();	
				Iterator<Commande> cIt = cIterator;
				
				if(c.getLongueur() > p.getLongueur() || c.getLargeur() > p.getLargeur())
				{
					c.setRejet(true);
					break; 
				}
				
				if(y + c.getLongueur() > p.getLongueur())
				{
					idPlanches++;
					y = 0;
				}
				
				x = c.getLargeur();
				decoupes.add(new Decoupe(0, y, idPlanches));
				
				while(cIt.hasNext()){
					
					if( c.getLongueur() < c2.getLongueur() ){
						c2 = cIt.next();
					}else{
						
						if( p.getLargeur() > x + c2.getLargeur()){
							decoupes.add(new Decoupe(0, y, idPlanches));
							x += c2.getLargeur();
							c2.decQuantite();
							if( c2.getQuantite() == 0)
								cIt.remove();
						}else{
							break;
						}
						
					}
					
				}
				
			} 
			Serialisation.svg("results."+p.getId()+".m1.svg", p.getLongueur(), p.getLargeur(), cList);
		}
	}
}
