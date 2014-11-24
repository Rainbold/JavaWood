package gestion.algorithme;

import gestion.bois.*;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.io.*;

public abstract class Algorithme {

	public static List<Planche> planchesSort(Set<Planche> planches) {
		List<Planche> pList = new ArrayList<>(planches); // Transformation du HashSet en liste
		Collections.sort(pList,new Comparator<Planche>(){ // Tri par implémentation directe de la fonction
			   @Override
			   public int compare(final Planche m1, Planche p1) {
				     if(m1.getPrix() <= p1.getPrix())
				    	 return -1;
				     else
				    	 return 1;
			     }
			 } );
		
		return pList;
	}

	public static List<Commande> commandesSort(Set<Commande> commandes) {
		List<Commande> cList = new ArrayList<>(commandes); // Transformation du HashSet en liste
		Collections.sort(cList,new Comparator<Commande>(){ // Tri par implémentation directe de la fonction
			   @Override
			   public int compare(final Commande m1, Commande p1) {
				     if(m1.getLongueur() < p1.getLongueur())
				    	 return 1;
				     else if(m1.getLongueur() > p1.getLongueur())
				    	 return -1;
				     else
				     {
				    	 if(m1.getLargeur() <= p1.getLargeur())
				    		 return 1;
				    	 else
				    		 return -1;
				     }
			     }
			 } );
		
		return cList; 
	}
	
	public static List<Commande> commandesSortLargeur(Set<Commande> commandes) {
		List<Commande> cList = new ArrayList<>(commandes); // Transformation du HashSet en liste
		Collections.sort(cList,new Comparator<Commande>(){ // Tri par implémentation directe de la fonction
			   @Override
			   public int compare(final Commande m1, Commande p1) {
				     if(m1.getLargeur() < p1.getLargeur())
				    	 return 1;
				     else if(m1.getLargeur() > p1.getLargeur())
				    	 return -1;
				     else
				     {
				    	 if(m1.getLongueur() <= p1.getLongueur())
				    		 return 1;
				    	 else
				    		 return -1;
				     }
			     }
			 } );
		
		return cList; 
	}
	
	public static List<Commande> commandesSortLargeur(List<Commande> commandes) {
		List<Commande> cList = new ArrayList<>(commandes); // Transformation du HashSet en liste
		Collections.sort(cList,new Comparator<Commande>(){ // Tri par implémentation directe de la fonction
			   @Override
			   public int compare(final Commande m1, Commande p1) {
				     if(m1.getLargeur() < p1.getLargeur())
				    	 return 1;
				     else if(m1.getLargeur() > p1.getLargeur())
				    	 return -1;
				     else
				     {
				    	 if(m1.getLongueur() <= p1.getLongueur())
				    		 return 1;
				    	 else
				    		 return -1;
				     }
			     }
			 } );
		
		return cList; 
	}

	public static void XMLParseFournisseur(String file, Set<Planche> planches) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory f = XMLInputFactory.newInstance();
		FileInputStream fis = new FileInputStream(file);
	    XMLStreamReader r = f.createXMLStreamReader(fis);
	    
    	int id = 0;
    	int longueur = 0;
    	int largeur = 0;
    	float prix = 0;
	   
	    int i = 0;
	    
		while(r.hasNext()) {
			int eventType = r.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                	if(i == 0 && r.getLocalName() != "fournisseurs")
            		{
                		// Todo throw an exception
            			System.err.println("Fichier " + file + " invalide");
            			System.exit(1);
            		}
                	
                	switch(r.getLocalName())
                	{
                		case "fournisseur":
                			int attributes = r.getAttributeCount();
                			for(int index=0; index<attributes; index++) {
                    			switch(r.getAttributeLocalName(index))
                    			{
	                				case "id":
	                					id = Integer.parseInt(r.getAttributeValue(index));
	                					break;
	                				case "longueur":
	                					longueur = Integer.parseInt(r.getAttributeValue(index));
	                					break;
	                				case "largeur":
	                					largeur = Integer.parseInt(r.getAttributeValue(index));
	                					break;
	                				case "prix":
	                					prix = Float.parseFloat(r.getAttributeValue(index).replace(",", "."));
	                					break;
                    			}
                			}
                			planches.add(new Planche(id, longueur, largeur, prix));
                			break;
                	}
                	break;
            }
            
            i++;
		}
	}

	public static void XMLParseCommande(String file, Set<Commande> commandes) throws FileNotFoundException, XMLStreamException {
		XMLInputFactory f = XMLInputFactory.newInstance();
		FileInputStream fis = new FileInputStream(file);
	    XMLStreamReader r = f.createXMLStreamReader(fis);
	    
    	int id = 0;
    	int longueur = 0;
    	int largeur = 0;
    	int quantite = 0;
	   
	    int i = 0;
	    int count = 0;
	    
		while(r.hasNext()) {
			int eventType = r.next();
            switch(eventType) {
                case XMLEvent.START_ELEMENT:
                	if(i == 0 && r.getLocalName() != "commandes")
            		{
                		// Todo throw an exception
            			System.err.println("Fichier " + file + " invalide");
            			System.exit(1);
            		}
                	
                	count = 0;
                	
                	switch(r.getLocalName())
                	{
                		case "commande":
                			int attributes = r.getAttributeCount();
                			for(int index=0; index<attributes; index++) {
                    			switch(r.getAttributeLocalName(index))
                    			{
	                				case "id":
	                					count++;
	                					id = Integer.parseInt(r.getAttributeValue(index));
	                					break;
	                				case "longueur":
	                					count++;
	                					longueur = Integer.parseInt(r.getAttributeValue(index));
	                					break;
	                				case "largeur":
	                					count++;
	                					largeur = Integer.parseInt(r.getAttributeValue(index));
	                					break;
	                				case "quantite":
	                					count++;
	                					quantite = Integer.parseInt(r.getAttributeValue(index));
	                					break;
                    			}
                			}
                			
                			if(count == 4)
                				commandes.add(new Commande(id, longueur, largeur, quantite));
                			break;
                	}
                	break;
            }
            
            i++;
		}
	}

	void triPrix() {
		
		File f1 = null;
		File f2 = null;
		int i = 1;
		
		while(true) {
			
			f1 = new File("results." + i + ".m1.xml");
			
			if(!f1.exists())
				break;
			
			f2 = new File("temp." + i );
				
		}
	}

	public static List<Commande> createCopy(List<Commande> arrSrc) {
		Iterator<Commande> i = arrSrc.iterator();
		Commande c;
		
		List<Commande> arrDest = new ArrayList<Commande>();
		
		while(i.hasNext())
		{
			c = i.next();
			arrDest.add(new Commande(c.getId(), c.getLongueur(), c.getLargeur(), c.getQuantite()));
		}
		
		return arrDest;
	}
}
