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
import java.lang.reflect.Array;
import java.nio.file.Files;

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

	public static void sortPrix(int nbPlanches) {
		
		File f1 = null;
		XMLInputFactory f = XMLInputFactory.newInstance();
		FileInputStream stream;
	    XMLStreamReader reader;
	    float prixM1[][] = new float[nbPlanches][2];
	    float prixM2[][] = new float[nbPlanches][2];
		
		for(int i = 0; i < 2 * nbPlanches; i++) {
			
			if(i < nbPlanches)
				f1 = new File("results.nt" + (i+1) + ".m1.xml");
			else
				f1 = new File("results.nt" + (i+1-nbPlanches) + ".m2.xml");
			
			if(f1.exists()) {
				try {
					stream = new FileInputStream(f1);
				
					reader = f.createXMLStreamReader(stream);
					
					if(i < nbPlanches) {
						prixM1[i][0] = i;
						prixM1[i][1] = -1;
					}
					else {
						prixM2[i-nbPlanches][0] = i-nbPlanches;
						prixM2[i-nbPlanches][1] = -1;
					}
					
					if(reader.hasNext() && reader.next() == XMLEvent.START_ELEMENT && reader.getLocalName() == "simulation") {
						if(i < nbPlanches)
							prixM1[i][1] = Float.parseFloat(reader.getAttributeValue(1));
						else
							prixM2[i-nbPlanches][1] = Float.parseFloat(reader.getAttributeValue(1));
					}
				
					reader.close();
					stream.close();
				} catch (NumberFormatException | XMLStreamException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		Comparator<float[]> arrayComparator = new java.util.Comparator<float[]>() {
			@Override
		    public int compare(float[] a, float[] b) {
		        return Float.compare(a[1], b[1]);
		    }
		};
		
		java.util.Arrays.sort(prixM1, arrayComparator);
		java.util.Arrays.sort(prixM2, arrayComparator);
		
		for(int i = 0; i < nbPlanches; i++) {
			if(prixM1[i][1] >= 0) {
				File t1 = new File("results.nt." + (Math.round(prixM1[i][0]+1)) + ".m1.xml");
				File t2 = new File("results." + (i+1) + ".m1.xml");
				try {
					Files.copy(t1.toPath(), t2.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
				//t1.deleteOnExit();
			}
		}
		for(int i = 0; i < nbPlanches; i++) {
			if(prixM2[i][1] >= 0) {
				File t1 = new File("results.nt." + (Math.round(prixM2[i][0]+1)) + ".m2.xml");
				File t2 = new File("results." + (i+1) + ".m2.xml");
				try {
					Files.copy(t1.toPath(), t2.toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
				//t1.deleteOnExit();
			}
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