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
		int numeroFichier = 1;
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
				
				decoupes.removeAllElements();
				
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
						if(( y+c.getLongueur()) > p.getLongueur())
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
			Algorithme.serialisation("testMethode1_" + numeroFichier + ".svg", p.getLongueur(), p.getLargeur(), cList);
			numeroFichier++;
		}
	}
	
	public static void serialisation(String filename, int longeur, int largeur, List<Commande> commandes) {
		
		FileWriter fwriter = null;
		try{
		     fwriter = new FileWriter(filename);
		}catch(IOException ex){
		    ex.printStackTrace();
		}
		
		int xPlanche = 30; // Distance horizontale des planches 
		int yPlanche = 30; // Distance verticale de la planche courante
		
		String contourTaille = "2px";
		String contourCouleur = "black";
		
		int interPlancheX = 50; // Distance entre 2 planches sur X
		int interPlancheY = 50; // Distance entre 2 planches sur Y
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
							// Nouvelle planche du fournisseur
							
							// Titre
							writer.writeStartElement("text");
							writer.writeAttribute("x", Integer.toString(nbPlanche * (largeur+interPlancheX) + xPlanche));
							writer.writeAttribute("y", Integer.toString(yPlanche - titreMarge));
							writer.writeAttribute("style", "font-size:" + textTaille + ";");
							writer.writeCharacters("Planche " + nbPlanche);
							writer.writeEndElement(); // text
							
							// Planche
							writer.writeStartElement("rect");
							writer.writeAttribute("width", Integer.toString(largeur));
							writer.writeAttribute("height", Integer.toString(longeur));
							writer.writeAttribute("x", Integer.toString(nbPlanche * (largeur+ interPlancheX) + xPlanche));
							writer.writeAttribute("y", Integer.toString(yPlanche));
							writer.writeAttribute("style", "fill:" + plancheCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
							writer.writeEndElement(); // rect
							
							nbPlanche = d.getIdPlanche();
						}
						
						// Decoupe
						writer.writeStartElement("rect");
						writer.writeAttribute("width", Integer.toString(c.getLargeur()));
						writer.writeAttribute("height", Integer.toString(c.getLongueur()));
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX()));
						writer.writeAttribute("y", Integer.toString(yPlanche + d.getY()));
						writer.writeAttribute("style", "fill:" + decoupeCouleur + "; stroke:" + contourCouleur + "; stroke-width:" + contourTaille + ";");
						writer.writeEndElement(); // rect
						
						// Numero de commande
						writer.writeStartElement("text");
						writer.writeAttribute("x", Integer.toString((d.getIdPlanche()-1) * (largeur+interPlancheX) + xPlanche + d.getX() + (c.getLargeur())/2 -5));
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