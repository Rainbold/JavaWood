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

	public static List<Planche> planchesSort(HashSet<Planche> planches) {
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

	public static List<Commande> commandesSort(HashSet<Commande> commandes) {
		List<Commande> cList = new ArrayList<>(commandes); // Transformation du HashSet en liste
		Collections.sort(cList,new Comparator<Commande>(){ // Tri par implémentation directe de la fonction
			   @Override
			   public int compare(final Commande m1, Commande p1) {
				     if(m1.getLongueur() <= p1.getLongueur())
				    	 return -1;
				     else
				    	 return 1;
			     }
			 } );
		
		return cList;
	}
	
	public static void serialisation(String filename, int longeur, int largeur, Set<Commande> commandes) {
		
		FileWriter fwriter = null;
		try{
		     fwriter = new FileWriter(filename);
		}catch(IOException ex){
		    ex.printStackTrace();
		}
		
		int xPlanche = 30; // Distance horizontale des planches 
		int yPlanche = 30; // Distance verticale de la planche courante
		int yFuturPlanche = 30;
		
		String contourTaille = "2px";
		String contourCouleur = "black";
		
		int interPlancheMarge = 50; // Distance entre 2 planches
		String plancheCouleur = "lightblue";
		
		int titreMarge = 10; // Nombre de pixel au dessus de la planche
		String textTaille = "14px";
		String textCouleur = "black";
		
		String decoupeCouleur = "yellow";
		
		int nbPlanche = 0;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		
		Iterator<Commande> ic = commandes.iterator();
		
		try {
			writer = factory.createXMLStreamWriter(fwriter);
		
			writer.writeStartDocument("1.0");
			
			writer.writeStartElement("svg");
			writer.writeAttribute("xmlns", "http://www.w3.org/2000/svg");
			writer.writeAttribute("version", "1.1");
			
			//TODO title, desc

			while(ic.hasNext()) {	
				Commande c = ic.next();
				
				if(!c.getRejet()) {
					Iterator<Decoupe> id = c.getDecoupes().iterator();
				 
					while(id.hasNext()) {
						Decoupe d = id.next();
						
						if(d.getIdPlanche() > nbPlanche) {
							nbPlanche = d.getIdPlanche();
							yPlanche = yFuturPlanche;
							
							// Nouvelle planche du fournisseur
							
							// Titre
							writer.writeStartElement("text");
							writer.writeAttribute("x", Integer.toString(xPlanche));
							writer.writeAttribute("y", Integer.toString(yPlanche - titreMarge));
							writer.writeAttribute("style", "font-size:" + textTaille + ";");
							writer.writeCharacters("Planche " + nbPlanche);
							writer.writeEndElement(); // text
							
							// Planche
							writer.writeStartElement("rect");
							writer.writeAttribute("width", Integer.toString(largeur));
							writer.writeAttribute("height", Integer.toString(longeur));
							writer.writeAttribute("x", Integer.toString(xPlanche));
							writer.writeAttribute("y", Integer.toString(yPlanche));
							writer.writeAttribute("style", "fill:" + plancheCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
							writer.writeEndElement(); // rect
							
							// Position futur d'une planche
							yFuturPlanche += interPlancheMarge + longeur;
						}
						
						// Decoupe
						writer.writeStartElement("rect");
						writer.writeAttribute("width", Integer.toString(c.getLargeur()));
						writer.writeAttribute("height", Integer.toString(c.getLongueur()));
						writer.writeAttribute("x", Integer.toString(xPlanche + d.getX()));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY()));
						writer.writeAttribute("style", "fill:" + decoupeCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
						writer.writeEndElement(); // rect
						
						// Numero de commande
						writer.writeStartElement("text");
						writer.writeAttribute("x", Integer.toString(xPlanche + d.getX() + (c.getLargeur())/2 -5));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY() + (c.getLongueur())/2));
						writer.writeAttribute("style", "font-size:" + textTaille + ";");
						writer.writeCharacters(Integer.toString(d.getIdPlanche()));
						writer.writeEndElement(); // text
					 
					}
				}
			}
			writer.writeEndElement(); // svg

			writer.writeEndDocument(); 

			//writer.flush();
			writer.close();
	    
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
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