package gestion.algorithme;

import  gestion.bois.*;
import javax.xml.stream.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Iterator;

public abstract class Algorithme {

	public void methode1(HashSet<Commande> commandes, HashSet<Planche> planches) {
	}
	
	public static void serialisation(String filename, int longeur, int largeur, HashSet<Commande> commandes) {
		
		int marge_x = 20;
		int marge_y = 20;
		int bande_noir = 5;
		int marge_planche = 20;
		int marge_titre = 10;
		
		int nb_planche = 0;
		
		Iterator<Commande> i = commandes.iterator();
		
		while(i.hasNext()) {
			 Commande e = i.next();
			 
			 //if(e.)
			 
			 
		}
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		
	    XMLStreamWriter writer;
		try {
			writer = factory.createXMLStreamWriter(System.out);
		

	    writer.writeStartDocument("1.0");

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
