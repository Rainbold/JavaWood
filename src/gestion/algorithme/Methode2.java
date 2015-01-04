package gestion.algorithme;

import gestion.bois.Commande;
import gestion.bois.Decoupe;
import gestion.bois.Planche;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Methode2 extends Algorithme {

	public static void process(Set<Commande> commandes, Set<Planche> planches) {
		
		List<Planche> pList = planchesSort(planches);
		
		Iterator<Commande> cIt, cItAux;
		Iterator<Planche> pIt = pList.iterator();
		
		int idPlanches = 1;
		int y = 0;
		int x = 0;
		int quantite = 0;
		int fichier = 1;

		Planche p;

		Commande c, cAux;
		
		List<Commande> cList = commandesSortLargeur(commandes);
		List<Commande> cListAux = new ArrayList<Commande>();
		
		cListAux = Algorithme.createCopy(cList);
		
		while(pIt.hasNext())
		{
			idPlanches = 1;
			y = 0;
			x = 0;
			p = pIt.next();	 
			cList = Algorithme.createCopy(cListAux);

			cIt = cList.iterator();
			c = new Commande(-1, -1, -1, -1);
			
			while(cIt.hasNext() || (c.getQuantite() > 0 && !c.getRejet()) )
			{	
				quantite = c.getQuantite();

				if(quantite <= 0 || c.getRejet())
				{
					c = cIt.next();
				}
				else
				{
					// Si la decoupe est plus large ou plus longue que la planche, la commande est rejetee
					if(c.getLongueur() > p.getLongueur() || c.getLargeur() > p.getLargeur())
					{
						c.setRejet(true);
					}
					else
					{
						// Si la prochaine decoupe est trop longue et ne rentre donc pas sur la planche, on change cette derniere
						if(y + c.getLongueur() > p.getLongueur())
						{
							idPlanches++;
							y = 0;
						}
						
						// On ajoute aux decoupes celle se trouvant sur le bord gauche de la planche
						c.getDecoupes().add(new Decoupe(x, y, idPlanches, c.getId()));
						c.decQuantite();
						x = c.getLargeur();
						
						cItAux = cList.iterator();
						
						// On parcourt les autres commandes pour voir s'il est possible d'en placer une a droite de la decoupe precedente 
						while(cItAux.hasNext()) {
							cAux = cItAux.next();
							quantite = cAux.getQuantite();
							if(quantite <= 0 || cAux.getRejet())
								continue;
							
							for(int j=0; j<quantite; j++)
							{
								if( cAux.getLongueur() <= c.getLongueur() )
								{
									if( p.getLargeur() > x + cAux.getLargeur())
									{
										cAux.getDecoupes().add(new Decoupe(x, y, idPlanches, cAux.getId()));
										x += cAux.getLargeur();
										cAux.decQuantite();
									}
									else
									{
										break;
									}
								}
								else
								{
									break;
								}
							}
						}
						x = 0;
						y += c.getLongueur();
					}
				}
			} 
			Resultat.xml("results.nt."+fichier+".m2.xml", idPlanches, p, 2, cList);
			Resultat.svg("results.nt."+fichier+".m2.svg", idPlanches, p, cList);
			fichier++;
		}
	}
}
