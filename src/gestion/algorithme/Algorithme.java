package gestion.algorithme;

import gestion.bois.*;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;
import java.io.*;





public abstract class Algorithme {

	public static void methode1(HashSet<Commande> commandes, HashSet<Planche> planches) {
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
		
		List<Commande> cList = new ArrayList<>(commandes); // Transformation du HashSet en liste
		Collections.sort(cList,new Comparator<Commande>(){ // Tri par implémentation directe de la fonction
			   @Override
			   public int compare(final Commande m1, Commande p1) {
				     if(m1.getLongueur() >= p1.getLongueur())
				    	 return -1;
				     else
				    	 return 1;
			     }
			 } );
		
		int nbPlanches = 1;
		int y = 0;
		int quantite = 0;
		Vector<Decoupe> decoupes;
		Decoupe d;
		
		Iterator<Planche> pIt = pList.iterator();
		while(pIt.hasNext())
		{
			Planche p = pIt.next();	 
			Iterator<Commande> cIt = cList.iterator();
			while(cIt.hasNext())
			{
				nbPlanches = 1;
				y = 0;
				Commande c = cIt.next();
				decoupes = c.getDecoupes();
				quantite = c.getQuantite();
				
				// Si la longueur ou la largeur de la decoupe est plus grande que la planche, la commande est rejetee
				if(c.getLongueur() > p.getLongueur() || c.getLargeur() > p.getLargeur())
				{
					c.setRejet(true);
					break; // On sort alors de la boucle et on passe a la commande suivante
				}
				else
				{
					for(int i=0; i<quantite; i++)
					{
						// Si la decoupe depasse de la planche actuelle, on en change
						if(y > p.getLongueur())
						{
							nbPlanches++;
							y = 0;
						}
						
						//System.out.println("[0, "+y+", "+nbPlanches+", "+c.getId()+"]");
						decoupes.add(new Decoupe(0, y, nbPlanches));
						y += c.getLongueur();
					}
				}
			} 
		}
	}
	
	public static void serialisation(String filename, int longeur, int largeur, HashSet<Commande> commandes) {
		
		int marge_x = 20;
		int marge_y = 20;
		
		int bande = 5;
		String couleur_bande = "black";
		
		int marge_planche = 20;
		int marge_titre = 10;
		
		String couleur_planche = "";
		
		String couleur_decoupe = "";
		
		int nb_planche = 0;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		
		Iterator<Commande> ic = commandes.iterator();
		
		try {
			writer = factory.createXMLStreamWriter(System.out, "UTF-8");
		
			writer.writeStartDocument("1.0");
			
			writer.writeStartElement("svg");
			writer.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			writer.writeAttribute("version", "1.1");
			//writer.writeAttribute("width", "300");
			
			//writer.writeAttribute("height", "200");
			
			//TODO title, desc
/*
			while(ic.hasNext()) {	
				Commande c = ic.next();
				commandes.
				if(c.getRejet()) {
					Iterator<Decoupe> id = c.getDecoupes().iterator();
				 
					while(id.hasNext()) {
						Decoupe d = id.next();
						
						if(d.getIdPlanche() > nb_planche) {
							nb_planche = d.getIdPlanche();
							
							writer.writeStartElement("rect");
							writer.writeAttribute("width", Integer.toString(largeur));
							writer.writeAttribute("height", Integer.toString(longeur));
							writer.writeAttribute("x", "300");
							writer.writeAttribute("y", "300");
							writer.writeAttribute("fill", "black");
							
							writer.writeEndElement(); // rect
						}
					 
					}
				}
			}
			writer.writeEndElement(); // svg

			writer.writeEndDocument(); 
	
	    
	    writer.writeStartElement("catalog");

	    writer.writeStartElement("book");

	    writer.writeAttribute("id", "1");

	    writer.writeStartElement("code");
	    writer.writeCharacters("I01");
	    writer.writeEndElement();

	    writer.writeStartElement("title");
	    writer.writeCharacters("This is the title");
	    writer.writeEndElement();

	    writer.writeStartElement("price");
	    writer.writeCharacters("$2.95");
	    writer.writeEndElement();

	    writer.writeEndDocument();
	    

	    writer.flush();
	    writer.close();
	    */
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public String Methode2() {
		
		return "";
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
                	
                	switch(r.getLocalName())
                	{
                		case "commande":
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
	                				case "quantite":
	                					quantite = Integer.parseInt(r.getAttributeValue(index));
	                					break;
                    			}
                			}
                			commandes.add(new Commande(id, longueur, largeur, quantite, false));
                			break;
                	}
                	break;
            }
            
            i++;
		}
	}

	
	public void Writer() {
	}
}