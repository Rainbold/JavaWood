package gestion.algorithme;

import gestion.bois.Commande;
import gestion.bois.Decoupe;
import gestion.bois.Planche;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class Methode1 extends Algorithme {

	public static void process(Set<Commande> commandes, Set<Planche> planches) {
		
		List<Planche> pList = planchesSort(planches);
		List<Commande> cList = commandesSort(commandes);
		
		int nbPlanches = 1;
		int y = 0;
		int quantite = 0;
		Vector<Decoupe> decoupes;
		
		Iterator<Planche> pIt = pList.iterator();
		while(pIt.hasNext())
		{
			nbPlanches = 1;
			y = 0;
			Planche p = pIt.next();	 
			Iterator<Commande> cIt = cList.iterator();
			int fichier = 1;
			while(cIt.hasNext())
			{
				Commande c = cIt.next();
				decoupes = c.getDecoupes();
				quantite = c.getQuantite();
				
				decoupes.removeAllElements();
				c.setRejet(false);
				
				// Si la longueur ou la largeur de la decoupe est plus grande que la planche, la commande est rejetee
				if(c.getLongueur() > p.getLongueur() || c.getLargeur() > p.getLargeur())
				{
					c.setRejet(true);
				}
				else
				{
					for(int i=0; i<quantite; i++)
					{
						// Si la decoupe depasse de la planche actuelle, on en change
						if(y + c.getLongueur() > p.getLongueur())
						{
							nbPlanches++;
							y = 0;
						}
						
						//System.out.println("[0, "+y+", "+nbPlanches+", "+c.getId()+"]");
						decoupes.add(new Decoupe(0, y, nbPlanches, c.getId()));
						y += c.getLongueur();
					}
				}
			} 
			Resultat.xml("results.nt."+fichier+".m1.xml", nbPlanches, p, 1, cList);
			Resultat.svg("results.nt."+fichier+".m1.svg", nbPlanches, p, cList);
			fichier++;
		}
	}
}
